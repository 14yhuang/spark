package com.ruozedata.bigdata.core03

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object MapPartitionApp {
  def main(args: Array[String]): Unit = {
    val sparkconf = new SparkConf().setMaster("local[2]").setAppName("asd")
    //一个context运行在一个jvm。
    val sc = new SparkContext(sparkconf)

    val student = new ListBuffer[String]()
    for (i<- 1 to 100){
      student +="ruoze" +i
    }

    val stus=sc.parallelize(student)

    //    stus.map(x => {
    //      val connection =  DBUtils.getConnection()
    //      println(connection + "~~~~~~~~~~~~")
    //      // TODO... 写出数据到数据库
    //
    //      DBUtils.returnConnection(connection)
    //    }).foreach(println)


    stus.mapPartitions(partition => {
      val connection =  DBUtils.getConnection()
      println(connection + "~~~~~~~~~~~~")
      // TODO... 写出数据到数据库

      DBUtils.returnConnection(connection)
      partition
    }).foreach(println)


    sc.stop()
  }
}
