package com.ruozedata.bigdata.kafka;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.UUID;

//kafka数据发送，producer
public class RuozeKafkaProducer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("serializer.class","kafka.serializer.StringEncoder");
        properties.put("metadata.broker.list","hadoop000:9092,hadoop000:9093,hadoop000:9094");

        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String,String> producer = new Producer<String, String>(producerConfig);

        String topic ="ruozeg5sparkkafka";
        for (int index=0; index<100;index++){
            producer.send(new KeyedMessage<String, String>(topic,index+"",index+"ruozeshuju:"+ UUID.randomUUID()));
        }

        System.out.println("若泽数据Kafka生产者生产数据完毕...");


    }

}
