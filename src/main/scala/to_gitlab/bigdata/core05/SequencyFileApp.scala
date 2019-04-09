package com.ruozedata.bigdata.core05


import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.BytesWritable
import org.apache.spark.{SparkConf, SparkContext}

class xxx
object scfileApp {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf()
      //在spark-shell运行的时候再指定，这里的优先级要比在shell里更高，appname
      //shell里有内置
      .setMaster("local[2]").setAppName("Spark05")
      //定义序列化方式
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      //要序列化注册的类
      .registerKryoClasses(Array(classOf[xxx]))

    val sc =new SparkContext(sparkConf)


    //读sequenceFile,参数，路径，key（字节的形式,因为seq底层key是二进制的）和value的类型
    //用textfile读，有部分数据会乱码
    val file = sc.sequenceFile[BytesWritable,String]("/user/hive/warehouse/page_views_sequencefile/000000_0")
    //直接查，数不对
    //file.foreach(println)
    //会把进制码打出来，这些数据不想要
    file.map(x=>(x._1.copyBytes(),x._2)).foreach(println)
    //x._1进制码去掉
    file.map(x=>x._2.split("\t")).map(x=>(x(0),x(1))).foreach(println)


    sc.stop()
  }

}
