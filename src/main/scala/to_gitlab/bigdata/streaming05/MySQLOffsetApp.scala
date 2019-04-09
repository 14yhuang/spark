package com.ruozedata.bigdata.streaming05

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scalikejdbc._
import scalikejdbc.config.DBs

object MySQLOffsetApp {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("MysqlOffsetApp")

    //从application.conf加载配置信息
    DBs.setup()

    //用来构建stream的参数，详情看源码
    val fromOffsets= DB.readOnly(implicit session =>{
      sql"select * from offsets_storage".map(rs => {
        (TopicAndPartition(rs.string("topic"), rs.int("partitions")), rs.long("offset"))
      }).list().apply()
    }).toMap


    val topic = ValueUtils.getStringValue("kafka.topics")
    val interval=10

    val kafkaParams=Map(
      "metadata.broker.list"->ValueUtils.getStringValue("metadata.broker.list"),
      "auto.offset.reset"->ValueUtils.getStringValue("auto.offset.reset"),
      "group.id"->ValueUtils.getStringValue("group.id")
    )
    val topics =topic.split(",").toSet
    val ssc=new StreamingContext(conf,Seconds(10))



    ////TODO... 去MySQL里面获取到topic对应的partition的offset

    //没有offset从头消费
    val messages=if(fromOffsets.size==0){
      KafkaUtils.createDirectStream(ssc,kafkaParams,topics)
      //有offset从指定位置消费
    }else{
      //把数据和元数据转换成需要的类型，模板
      val messageHandler = (mm:MessageAndMetadata[String,String]) => (mm.key(),mm.message())
      KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder,(String,String)](ssc,kafkaParams,fromOffsets,messageHandler)
    }

    messages.foreachRDD(rdd=>{
      if(!rdd.isEmpty()){
        println("---the count---"+rdd.count())

        val offsetRanges=rdd.asInstanceOf[HasOffsetRanges].offsetRanges
        offsetRanges.foreach(x=>{
          println(s"--${x.topic}---${x.partition}---${x.fromOffset}---${x.untilOffset}---")

          //替换到mysql最新的offset
          DB.autoCommit(implicit session =>{
            sql"replace into offsets_storage(topic,groupid,partitions,offset) values(?,?,?,?)"
              .bind(x.topic,ValueUtils.getStringValue("group.id"),x.partition,x.untilOffset)
              .update().apply()
          })
        })
      }
    })

    ssc.start()
    ssc.awaitTermination()
  }

}
