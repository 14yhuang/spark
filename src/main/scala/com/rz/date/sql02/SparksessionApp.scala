package com.rz.date.sql02

import org.apache.spark.sql.SparkSession

object SparksessionApp {
  //创建dataframe
  def main(args: Array[String]): Unit = {
    val sessionApp = SparkSession.builder().appName("SparksessionApp")
      .master("local[2]")
      //开启hive支持
      .enableHiveSupport()
      .getOrCreate()

    //sessionApp.sparkContext.parallelize(Array(1,2,3)).collect().foreach(println)

    //sessionApp.read.json("").show()

     //读hive
//    sessionApp.sql("show databases").show()
//    sessionApp.sql("select * from page_views limit 10").show()
    //也可以这样读hive，解析成dataframe
//    sessionApp.table("page_views limit 10").show()

    //将rdd转换为dataframe
    //得导入隐式转换

    import sessionApp.implicits._
    val g5 = sessionApp.sparkContext.
      parallelize(Array(("1", "豆豆"), ("2", "牛哥"), ("34", "进取")))
        .toDF("id","name")
    g5.show()
    g5.printSchema()


//    +---+----+
//    | id|name|
//    +---+----+
//    |  1|豆豆|
//      |  2|牛哥|
//      | 34|进取|
//      +---+----+
//
//    root
//    |-- id: string (nullable = true)
//    |-- name: string (nullable = true)

    sessionApp.stop()
  }
}
