package com.ruozedata.bigdata.core05

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.hadoop.io.BytesWritable
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

case class Student(id:Int,name:String,age:Int)
object serializeApp {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf()
      //在spark-shell运行的时候再指定，这里的优先级要比在shell里更高，appname
      //shell里有内置
    //  .setMaster("local[2]").setAppName("Spark05")
      //定义序列化方式,写在shell里
//      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
//      //要序列化注册的类
      .registerKryoClasses(Array(classOf[Student]))

    val sc =new SparkContext(sparkConf)




    val students = ListBuffer[Student]()
    for (i<- 0 to 1000000){
      students.append(Student(i,"ruozedata_"+i,39))
    }

    val studentRDD = sc.parallelize(students)

    //带序列化的cache
    studentRDD.persist(StorageLevel.MEMORY_ONLY_SER)
    studentRDD.count()

    //跑完等会，看spark web，没开历史
    Thread.sleep(1000*20)
    sc.stop()
  }

}
