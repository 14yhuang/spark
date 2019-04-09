package com.ruozedata.bigdata.core4
import java.io.{File, PrintWriter}
import java.net.URI

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

object task_dirty {
  def main(args: Array[String]): Unit = {
    val spark=new SparkConf()
      .setMaster("local[2]")
      .setAppName("Accumulator")

    val sc = new SparkContext(spark)

    val file = sc.textFile("file:///F:/ruozedata_workplace/g5-spark/generatefile")
    val save_dirty="hdfs://hadoop000:9000/user/14yhuang/g5/dirty.txt"

//    val writer = new PrintWriter(new File(
//      save_dirty
//    ))


    val errorcount = sc.longAccumulator("dirty data")
    val allcount = sc.longAccumulator(name = "all data")
    val errorfile = ListBuffer[String]()

    val valResult = file.map(x=>{
      val tmp = x.split("\t")
      allcount.add(1)
      //^匹配输入字符串开始的位置。$匹配输入字符串结尾的位置。
      val datetype ="""^\d{4}-[01][0-9]-[0123][0-9]$""".r

      //findAllIn返回一个生成器
      val date = for(x<-datetype findAllIn tmp(1)) yield x
      val dateList = date.toList

      val liuliang = liu(tmp(2))

      if (dateList.size == 0 || liuliang == None){
        errorcount.add(1)
        (tmp(0),tmp(1),tmp(2))
      }else {
        None
      }

    }).filter(_ != None)

    //valResult.collect().foreach(println)

    val uri = new URI("hdfs://192.168.137.190:9000" )
    val fileSystem = FileSystem.get(uri,sc.hadoopConfiguration,"hadoop")
    if (fileSystem.exists(new Path(save_dirty))){
      fileSystem.delete(new Path(save_dirty),true)
    }

    valResult.saveAsTextFile(save_dirty)
    val asd = sc.textFile(save_dirty).foreach(println)

//    //writer.print(valResult.collect().foreach(println))
//        writer.flush()
//        writer.close()


//    val ew="""\d""".r findAllIn "3,2,1"
//    println(ew.toList)

    //println("all:"+valResult.count())
    //for (x<- errorfile) yield println(x)

    val rightcount = allcount.value-errorcount.value
    println("right:"+rightcount)
    println("dirty:"+errorcount.value)

    sc.stop()
  }

  def liu(x:String)={
    try{
      var liu = x.toLong

    }
    catch {
      case e: Exception=>None
    }
  }
}
