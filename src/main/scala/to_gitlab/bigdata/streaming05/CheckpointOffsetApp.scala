package com.ruozedata.bigdata.streaming05

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Duration, Seconds, StreamingContext}

object CheckpointOffsetApp {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("CheckpointOffsetApp")

    //没有会自动创建
    val checkpointPath = "hdfs://hadoop000:9000/offset_g5/checkpoint"
    val topic="ruozeg5sparkkafka"
    val interval =10
    val kafkaParams = Map[String, String]("metadata.broker.list"->"hadoop000:9092","metadata.broker.list"->"hadoop000:9093","metadata.broker.list"->"hadoop000:9094","auto.offset.reset"->"smallest")
    val topics = topic.split(",").toSet


    def function2CreateStreamingContext()={
      val ssc = new StreamingContext(conf,Seconds(10))
      //[]里是[key class], [value class], [key decoder（解码） class], [value decoder class] ]
      //(streamingContext, [map of Kafka parameters], [set of topics to consume])
      val messages = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams, topics)
      ssc.checkpoint(checkpointPath)
      messages.checkpoint(Duration(8*10.toInt*1000))

      messages.foreachRDD(rdd=>{
        if (!rdd.isEmpty()){
          println("------asd------"+rdd.count())
        }
      })
      ssc
    }

    //如果检查点数据存在就根据检查点数据重建context，如果不存在就根据第二个参数构建context
    val ssc =StreamingContext.getOrCreate(checkpointPath,function2CreateStreamingContext)
    ssc.start()
    ssc.awaitTermination()


  }

}
