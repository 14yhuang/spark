package com.ruozedata.bigdata.core01

import org.apache.spark.{SparkConf, SparkContext}

object SparkContextApp {
  def main(args: Array[String]): Unit = {
    //本地模式
    val sparkconf = new SparkConf().setMaster("local[2]").setAppName("asd")
    //一个context运行在一个jvm。
    val sc = new SparkContext(sparkconf)


    sc.stop()
  }
}
