package com.ruozedata.bigdata.streaming03

import java.sql.DriverManager

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ForeachRDDApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("ForeachRDDApp")
    val ssc = new StreamingContext(conf,Seconds(10))


    val lines=ssc.socketTextStream("hadoop000",9999)
    val results=lines.flatMap(_.split(",")).map((_,1)).reduceByKey(_+_)

    //(ruoze,2)(jepson,1)
    results.foreachRDD(rdd =>{
      rdd.foreachPartition(partition=>{
        val connection = createConnection()

        partition.foreach(pair=>{
                  val sql=s"insert into wc(word,count) values('${pair._1}',${pair._2})"
                  connection.createStatement().execute(sql)
                  connection.close()
        })
      })


//      rdd.foreach(pair =>{
//        //connection必须在worker端创建，但是会导致每条rdd都会创建一次，所以应当用foreachpartition
//        val connection = createConnection()
//        val sql=s"insert into wc(word,count) values('${pair._1}',${pair._2})"
//        connection.createStatement().execute(sql)
//        connection.close()
//      })
    })

    ssc.start()
    ssc.awaitTermination()
  }

  def createConnection() ={
    Class.forName("com.mysql.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://hadoop000:3306/g5_spark","root","123456")
  }
}
