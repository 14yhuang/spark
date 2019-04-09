package com.ruozedata.bigdata.core03

import org.apache.spark.{SparkConf, SparkContext}

object wc {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf()
      .setMaster("local[2]").setAppName("WcApp")
    val sc = new SparkContext(sparkConf)

//    val lines = sc.textFile("")
//    val words = lines.flatMap(x=>x.split(","))
    val words = sc.parallelize(Array("a","b","c",'d'))
    val pairs = words.map((_,1))
    //[string,int]的形式
    pairs.reduceByKey((x,y) => (x+y)).collect().foreach(println)

//    val a = sc.parallelize(List(("a",10),("b",4),("a",10),("a",10),("b",20))).mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2)).collect()//mapValues(x => (x._1, x._2, x._1.toDouble / x._2.toDouble)).collect()
//    a.foreach(println)

    //[string,Iterable(int)]的形式
//    pairs.groupByKey().map(x => (x._1, x._2.sum))
    sc.stop()
  }
}
