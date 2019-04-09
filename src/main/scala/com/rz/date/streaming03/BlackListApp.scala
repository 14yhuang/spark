package com.rz.date.streaming03

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object BlackListApp {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf()
      .setMaster("local[2]")
      .setAppName("BlackListApp")

    val sc = new SparkContext(sparkConf)

    //1:名字 2：info
    val input=new ListBuffer[(String,String)]
    input.append(("doudou","doudou info"))
    input.append(("huahua","huahua info"))
    input.append(("ice","ice info"))
    input.append(("spy","spy info"))

    val inputRDD=sc.parallelize(input)

    //黑名单,
    val blackTuple=new ListBuffer[(String,Boolean)]
    blackTuple.append(("doudou",true))
    blackTuple.append(("huahua",true))
    val blacksRDD=sc.parallelize(blackTuple)

    //非黑名单的就是none
    /*
    (doudou,(doudou info,Some(true)))
    (spy,(spy info,None))
    (huahua,(huahua info,Some(true)))
    (ice,(ice info,None))
     */
    //inputRDD.leftOuterJoin(blacksRDD).collect().foreach(println)

    inputRDD.leftOuterJoin(blacksRDD).filter(x=>{
      x._2._2.getOrElse(false) !=true

    }).collect().foreach(println)


    sc.stop()
  }
}
