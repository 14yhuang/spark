package com.ruozedata.bigdata.sql06

import org.apache.spark.sql.SparkSession

object CatalogApp {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("DataSourceAPIApp")
      .master("local[2]")
      .getOrCreate()
import sparkSession.implicits._
    val catalog=sparkSession.catalog
    catalog.listDatabases().show(false)//.select("name","locationUri").show(false)

    catalog.listTables("default").show(false)
    //catalog.listColumns("default","ruoze_emp").show(false)


 //   sparkSession.read.format("csv").option("header","true").option("inferSchema","true").csv()
//
//    //使用csv文件里第一行带的schema信息，返回dataframe类型
//    val df = sparkSession.read.format("csv").option("header","true").option("inferSchema","true").csv("file:///home/hadoop/data/sales.csv")
//    //转成dataset，使用下面的case class
    val ds = sparkSession.read.format("csv").option("header","true").option("inferSchema","true").csv("file:///home/hadoop/data/sales.csv").as[Sales]
    ds.show()

//    val selectDF = df.select("itemId")
//    val selectDS = ds.select("itemId") //.show(false) // 运行时异常
//    //ds.map(_.itemId).show(false)
//
//    selectDF.queryExecution.optimizedPlan.numberedTreeString
//    selectDS.queryExecution.optimizedPlan.numberedTreeString




    sparkSession.stop()
  }

  case class Sales(transactionId:Int,customerId:Int,itemId:Int,amountPaid:Double)
}
