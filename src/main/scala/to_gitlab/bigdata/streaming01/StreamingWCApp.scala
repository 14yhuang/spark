package com.ruozedata.bigdata.streaming01

import org.apache.spark._
import org.apache.spark.streaming.{Seconds, StreamingContext}


object StreamingWCApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("StreamingWCApp")
    //streaming数据多久被切成一批,必须设置
    val ssc = new StreamingContext(conf, Seconds(10))

    //TCP作为源
//    val lines = ssc.socketTextStream("hadoop000",9999)


    val lines=ssc.textFileStream("hdfs://hadoop000:9000/ss/logs")
    val results = lines.flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)
    //打印前10条
    results.print()
    //lines.print()

    //启动streaming
    ssc.start()
    ssc.awaitTermination()
  }
}
