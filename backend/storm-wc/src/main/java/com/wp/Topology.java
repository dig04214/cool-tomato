package com.wp;

import com.wp.bolt.SplitBolt;
import com.wp.bolt.WordCountBolt;
import com.wp.spout.KafkaSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Topology {
    private static final String KAFKA_SPOUT_ID = "kafka-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-bolt";
    private static final String TOPOLOGY_NAME = "word-count-topology";
    public static void main(String[] args) {
        KafkaSpout kafkaSpout = new KafkaSpout();
        SplitBolt splitBolt = new SplitBolt();
        WordCountBolt wordCountBolt = new WordCountBolt();

        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(KAFKA_SPOUT_ID, kafkaSpout, 2);
        builder.setBolt(SPLIT_BOLT_ID, splitBolt, 2).shuffleGrouping(KAFKA_SPOUT_ID);
        builder.setBolt(COUNT_BOLT_ID, wordCountBolt, 2).shuffleGrouping(SPLIT_BOLT_ID);


        Config config = new Config();
        config.setNumWorkers(3);

        try {
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        }catch (AlreadyAliveException ae){
            ae.printStackTrace();
        }catch (InvalidTopologyException ie){
            ie.printStackTrace();
        }catch (AuthorizationException e){
            e.printStackTrace();
        }
    }
}