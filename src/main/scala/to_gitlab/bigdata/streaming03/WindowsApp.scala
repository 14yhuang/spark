package com.ruozedata.bigdata.streaming03

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowsApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("WindowsApp")
    val ssc = new StreamingContext(conf,Seconds(5))

    val lines = ssc.socketTextStream("hadoop000",9999)
    lines.flatMap(_.split(",")).map((_,1)).reduceByKeyAndWindow((a:Int,b:Int)=>(a+b),Seconds(10),Seconds(5)).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
