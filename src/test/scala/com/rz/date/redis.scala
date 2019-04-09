package com.rz.date

import com.redislabs.provider.redis._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object redis {
  def main(args: Array[String]): Unit = {

//    val ssc = SparkSession.builder().appName("redis")
//      .master("local[2]").config("redis.host","192.168.137.190")
//      .config("redis.port","6379")
//      .getOrCreate()

    val conf = new SparkConf().setMaster("local[2]").setAppName("redis").set("spark.redis.host","192.168.137.190").set("spark.redis.port","6379")
    val ssc = new SparkContext(conf)


    //val redisDB=("192.168.137.190",6379)
    ssc.fromRedisKV(1)


    ssc.stop()
  }


}
