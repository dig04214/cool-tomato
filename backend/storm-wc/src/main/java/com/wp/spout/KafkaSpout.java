package com.wp.spout;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private KafkaConsumer<String, String> consumer;
    private static final String TOPIC_NAME = "chat";

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "word-count"); // group-id 설정
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // key deserializer
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer"); // value deserializer
        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList(TOPIC_NAME));
    }

    @Override
    public void nextTuple() {
        try {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.println(record.value());
                JSONObject jsonObject = new JSONObject(record.value());
                String roomId = jsonObject.get("roomId").toString();
                String message = jsonObject.get("message").toString();
                byte[] bytes = message.getBytes();
                message = new String(bytes);
                this.collector.emit(new Values(roomId, message));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("roomId", "message"));
    }
}
