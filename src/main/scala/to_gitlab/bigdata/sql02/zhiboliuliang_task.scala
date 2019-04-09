package com.ruozedata.bigdata.sql02

import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession, types}
import org.apache.spark.sql.functions._

object zhiboliuliang_task {
  def main(args: Array[String]): Unit = {
    val session  = SparkSession.builder()
      .master("local[2]")
      .appName("zhibo")
      .getOrCreate()

    //val file = session.read.format("text").load("file:///F:\\ruozedata_workplace\\g5-spark\\zhibo_liuliang.txt")
    //val file = session.read.text("file:///F:\\ruozedata_workplace\\g5-spark\\zhibo_liuliang.txt")
    val file = session.sparkContext.textFile("file:///F:\\\\ruozedata_workplace\\\\g5-spark\\\\zhibo_liuliang.txt")

//    file.rdd.map(line=>{
//      line(0)
//    }).collect().foreach(println)

//    //file.flatMap(_.split(",")).collect().foreach(println)
    import session.implicits._
//
    val df = file.map(x=>{
      val temp = x.split(",")
      //去头尾空格,截取一段字符串,从哪个位置开始找到哪个元素所在索引
      (temp(0),temp(1).trim.substring(0,temp(1).trim.indexOf("-",7)),temp(2).toLong)
      //temp
    }).map(x=>Row(x._1,x._2,x._3))
      //.toDF("id","date","liu")
//
    val struct = StructType(Array(
      StructField("id",StringType,false),
      StructField("date",StringType,false),
      StructField("liu",LongType,true)
    ))

    val imp = session.createDataFrame(df,struct)
    //imp.show()

      val asd = imp.groupBy('date,'id).agg(sum('liu) as "month").select("id","date","month").show()
//
//      asd.createOrReplaceTempView("biao")
//
//      val tmp = session.sql("select id,date,month,sum(month) as all from biao group by id,date,month  ").show()

    session.stop()
  }
}
