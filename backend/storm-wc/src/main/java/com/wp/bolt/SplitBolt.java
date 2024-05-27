package com.wp.bolt;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class SplitBolt extends BaseRichBolt {
    private OutputCollector collector;
    private Komoran komoran;
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.komoran = new Komoran(DEFAULT_MODEL.FULL);
    }

    @Override
    public void execute(Tuple tuple) {
        String roomId = tuple.getStringByField("roomId");
        String message = tuple.getStringByField("message");
        System.out.println("wc " + roomId + " " + message);
        KomoranResult analyzeResultList = komoran.analyze(message);
        List<String> nounList = analyzeResultList.getNouns();
        for (String noun: nounList) {
            this.collector.emit(new Values(roomId, noun));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("roomId", "noun"));
    }
}
