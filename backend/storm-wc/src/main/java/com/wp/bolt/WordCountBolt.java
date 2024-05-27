package com.wp.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

public class WordCountBolt extends BaseRichBolt {
    private OutputCollector collector;
    JedisPoolConfig jedisPoolConfig;
    JedisPool pool;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.collector = outputCollector;
        this.jedisPoolConfig = new JedisPoolConfig();
        this.pool = new JedisPool(this.jedisPoolConfig, "MASKING_URL", 6379, 1000, "MASKING_PASSWORD");
    }

    @Override
    public void execute(Tuple tuple) {
        String roomId = tuple.getStringByField("roomId");
        String noun = tuple.getStringByField("noun");
        Jedis jedis = this.pool.getResource();
        System.out.println("wc " + roomId + " " + noun);
        jedis.zincrby(roomId, 1, noun);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
