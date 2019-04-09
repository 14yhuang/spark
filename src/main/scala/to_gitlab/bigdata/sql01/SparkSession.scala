package com.ruozedata.bigdata.sql01
import org.apache.spark.sql.SparkSession

object SparkSessionApp {
  def main(args: Array[String]): Unit = {
    //SparkSession也是调用sparkcontext
    val session = SparkSession.builder()
      .master("local[2]")
      .getOrCreate()

  //  session.sparkContext.parallelize(Array(1,2,3)).foreach(println)

    session.stop()
  }
}
