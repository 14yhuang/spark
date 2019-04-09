package sparkrz

import org.apache.spark.sql.{SaveMode, SparkSession}

object LogCol {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().getOrCreate()

    val time=spark.sparkContext.getConf.get("spark.loadtime")
    val day=time.substring(0,8)
    val hour=time.substring(8,18)
    val input=s"hdfs://192.168.137.190:9000/spark/$time"
    val output=s"hdfs://192.168.137.190:9000/spark/day"


    var logDF = spark.read.format("text").load(input)

    logDF = spark.createDataFrame(logDF.rdd.map(x => {
      LogParser.parseLog(x.getString(0))
    }),LogParser.struct)

    //logDF.show(false)

    logDF.write.option("compression","none")
        .format("parquet")
        .mode(SaveMode.Overwrite).save(output)
    spark.stop()
  }



}
