package com.ruozedata.bigdata

import org.apache.spark.{SparkConf, SparkContext}


object tt{
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]").setAppName("ForeachPartitionApp")
    val sc = new SparkContext(sparkConf)

  val ad= List(1,2,3)
    println(ad.sum)

  }
}



