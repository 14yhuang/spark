package com.ruozedata.bigdata.sql03

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

object DataSourceApiApp {
  def main(args: Array[String]): Unit = {
    val sparkSession=SparkSession.builder()
      .appName("DSApp")
      .master("local[2]")
      .getOrCreate()

    //sparkSession.createDataFrame()


    val jdbcUrl = "jdbc:mysql://192.168.137.190:3306/ruozedb"
    val table="dept"
    val protity = new Properties()
    protity.setProperty("user","ruoze")
    protity.setProperty("password","123456")

    sparkSession.read.jdbc(jdbcUrl,table,protity).show()
//
//    sparkSession.read.format("jdbc").option("url","jdbc:mysql://192.168.137.190:3306/ruozedb")
//        .option("table","dept").option("user","ruoze").option("password","123456").load().show()


//    //使用外部数据源,用来读取非流数据，返回一个dataframe
//    sparkSession.read.format("text").load("/home/hadoop/data/student.txt").show(false)
//    //多个路径的文件叠加
//    sparkSession.read.format("text").load("/home/hadoop/data/student.txt","/home/hadoop/data/asd.txt").show(false)
//
//    //简化写法
    //sparkSession.read.text("/home/hadoop/data/student.txt").show(false)
    //    sparkSession.read.parquet()
//    //只能读parquet文件
//    sparkSession.read.load("")
//    sparkSession.conf.get("spark.sql.sources.default")
//
//    //path可以放到option里
//    sparkSession.read.format("text").option("path","/home/hadoop/data/student.txt").load().show()
//
//
//    val df=sparkSession.read.json("")
////    //标准写写法,save只能指定到目录，不能指定文件
////    df.select("").write.format("json").save("").show(false)
////    //savemode，如果外部数据源已有此文件，报错,要导入SaveMode
////    df.select("").write.format("json").mode(SaveMode.Overwrite).save("").show(false)
////    //这样就不用导包了
//    df.select("").write.format("json").mode("Overwrite").save("").show(false)
////
////    //直接写到hive里
//    df.select("name").write.saveAsTable("xxx")
////    //读之
//    sparkSession.table("xxx")
////
////    //指定分割符,第一行做表头,自动推测schema类型
//    sparkSession.read.format("csv").option("inferSchema","true").option("header","true").option("sep",";").load().show()
//
//

    sparkSession.stop()

  }

}
