package com.rz.date.streaming03

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable.ListBuffer

object TransformApp {
  def main(args: Array[String]): Unit = {
    val conf=new SparkConf().setMaster("local[2]").setAppName("TransformApp")
    val ssc = new StreamingContext(conf,Seconds(10))

    val blackTuple=new ListBuffer[(String,Boolean)]
    blackTuple.append(("doudou",true))
    blackTuple.append(("huahua",true))
    val blacksRDD=ssc.sparkContext.parallelize(blackTuple)


    val lines=ssc.socketTextStream("hadoop000",9999)
    //xx,xx info => (xx,(xx,xx info))
    //返回Dstream

    lines.map(x=>(x.split(",")(0),x)).transform(rdd=>{
      rdd.leftOuterJoin(blacksRDD).filter(x=>{
        x._2._2.getOrElse(false) !=true
      }).map(_._2._1)
    }).print()


    ssc.start()
    ssc.awaitTermination()
  }
}
