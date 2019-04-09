package com.ruozedata.bigdata.streaming04

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object DirectKafkaApp {

  def main(args: Array[String]) {
    // 准备工作
    val conf = new SparkConf().setMaster("local[2]").setAppName("DirectKafkaApp")
    val ssc = new StreamingContext(conf, Seconds(10))

    val topic = "ruozeg5sparkkafka"
    val kafkaParams = Map[String, String]("metadata.broker.list"->"hadoop000:9092","metadata.broker.list"->"hadoop000:9093","metadata.broker.list"->"hadoop000:9094")
    val topics = topic.split(",").toSet
    //[]里是[key class], [value class], [key decoder（解码） class], [value decoder class] ]
    //(streamingContext, [map of Kafka parameters], [set of topics to consume])
    val messages = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams, topics)

    messages.map(_._2) // 取出value
      .flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)
      .print()

    ssc.start()
    ssc.awaitTermination()

  }

}
