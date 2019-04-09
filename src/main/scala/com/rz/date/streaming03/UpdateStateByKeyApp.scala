package com.rz.date.streaming03

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}


import scala.collection.mutable.ListBuffer

object UpdateStateByKeyApp {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("local[2]").setAppName("UpdateStateByKeyApp")
    val ssc = new StreamingContext(conf,Seconds(10))

    //记录以前批次的,指定到当前目录
    ssc.checkpoint("hdfs://hadoop000:9000/ss/logs")

    val lines=ssc.socketTextStream("hadoop000",9999)
    val results=lines.flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)

    val state = results.updateStateByKey(updateFunction)
    state.print()

    ssc.start()
    ssc.awaitTermination()
  }

  def updateFunction(currentValues:Seq[Int],preValues:Option[Int]):Option[Int] = {
    val curr = currentValues.sum
    val pre = preValues.getOrElse(0)

    Some(curr + pre)
  }


}
