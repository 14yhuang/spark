package com.rz.date.streaming04

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ReceiverKafkaApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("ReceiverKafkaApp")
    val ssc = new StreamingContext(conf,Seconds(10))

    val topic ="ruozeg5sparkkafka"
    //注意kafka分区和partition分区无关
    val numPartitions=1

    val zkQuorum="192.168.137.190:2181,192.168.137.190:2182,192.168.137.190:2183/kafka"
    var groupId="test_g5"
    //元祖转map
    val topics =topic.split(",").map((_,numPartitions)).toMap

    //返回个receiver，带有kafka keyvalue的DStream
    val messages = KafkaUtils.createStream(ssc,zkQuorum,groupId,topics)

    //第一列是null
    messages.map(_._2)
      .flatMap(_.split(",")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
