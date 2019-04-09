package com.ruozedata.bigdata.core03

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object foreachpartition {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]").setAppName("ForeachPartitionApp")
    val sc = new SparkContext(sparkConf)

    val students = new ListBuffer[String]()
    for(i <- 1 to 100) {
      students += "若泽数据实战班五期：" + i
    }

    val stus = sc.parallelize(students, 4)

    //    stus.foreach(x => {
    //      val connection =  DBUtils.getConnection()
    //      println(connection + "~~~~~~~~~~~~")
    //      // TODO... 写出数据到数据库
    //
    //      DBUtils.returnConnection(connection)
    //    })


    stus.foreachPartition(partition => {
      val connection =  DBUtils.getConnection()
      println(connection + "~~~~~~~~~~~~")
      // TODO... 写出数据到数据库

      DBUtils.returnConnection(connection)
    })
    sc.stop()
  }
}
