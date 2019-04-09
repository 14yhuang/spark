package com.ruozedata.bigdata.sql02
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

object DFApp {

  def main(args: Array[String]): Unit = {
    val session=SparkSession.builder().
      appName("DF").
      master("local[2]")
      .getOrCreate()


    inferReflection(session)
    //programmatically(session)

    session.stop()
  }

  def programmatically(session: SparkSession)={

    val info = session.sparkContext.textFile("file:///F:/asd.txt")
    import session.implicits._
    //1.从原始rdd创建一个row类型rdd
    val rdd = info.map(_.split(",")).map(x=>Row(x(0),x(1),x(2).toLong))
    // 2.创建一个schema由StructType代表匹配上一步的rdd
    val struct = StructType(Array(
      //第三个参数是是否能为空
      StructField("ip",StringType,false),
      StructField("domain",StringType,false),
      StructField("response",LongType,false)
    ))
    //3.把schema作用在rdd上
    val df = session.createDataFrame(rdd,struct)
    df.rdd.collect()
    //df.show()


    //df.select("ip").show(2)
    //sparksql的函数,需要导入functions  import org.apache.spark.sql.functions._
    //agg使用聚合函数
    //df.groupBy('domain).agg(sum("response") as ("rs") ).select("domain","rs").show()

    //自定义函数
    //视图，零时表
    //df.createOrReplaceTempView("infos")
    //注册函数
    //函数名，对参数作用自定义功能,这里的ip是里面getInfo函数的传参
    session.udf.register("province" ,(ip:String)=>{
      IPUtils.getInfo(ip)
    })

    //增加一列。
    val asd = df.withColumn("isp",col("response")*3)
    asd.createOrReplaceTempView("infos2")
    session.sql("select ip,domain,sum(response) as responseSize,province(ip) as province ,sum(isp) from infos2 group by ip,domain,province(ip) limit 5").show()



  }


  def inferReflection(session: SparkSession)={
    import session.implicits._
    //创建RDD
    val info = session.sparkContext.textFile("file:///F:/asd.txt")
    //info.toDF()

    //info.take(3).foreach(println)
    //作用caseclass，这样相当于给每个参数有对应的类型和名称
    //scala映射支持自动转换一个包含case class 的RDD成为一个DataFrame
    val df = info.map(_.split(",")).map(x=>Info(x(0),x(1),x(2).toLong)).toDF()
    //df.groupBy("ip","domain").sum("liu").show()

    //注册一个视图，相当于零时表，这样就可以用sql去查询
    df.createOrReplaceTempView("info")
    val infoDF=session.sql("select ip,domain,sum(liu) as liu from info group by domain,ip")
    //infoDF.show()
    //dataframe要作用一个RDD的算子需要加rdd
    //getAs是拿到domain的所有值
    //infoDF.rdd.map(x=>x.getAs[String]("domain")).collect().foreach(println)
    //不用转换为rdd也可以,返回dataset形式
    //infoDF.map(x=>x.getAs[String]("domain")).show()
    //infoDF.as[Info].show()//map(_.domain)
    //索引方式也可以
    //infoDF.rdd.map(x=>x(0)).collect().foreach(println)



  }

}
//这个case class定义的就是表的schema
case class Info(ip:String,domain:String,liu:Long)

object IPUtils{
  def getInfo(ip:String) = {
    ("深圳市","联通")
  }
}
