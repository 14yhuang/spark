package com.ruozedata.bigdata.core02

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * 这里我们是要把程序提交到spark上执行，
  * 所以读取文件和写文件应当由传入参数指定而不是写死
  * 输入：args(0)
  * 输出：args(1)
  */
object LogApp {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf()
      //在spark-shell运行的时候再指定，这里的优先级要比在shell里更高，appname
      //shell里有内置
      .setMaster("local[2]").setAppName("Spark02")

    val sc =new SparkContext(sparkConf)

    //写到hdfs要检测文件是否存在，存在要删掉，不然报错
    //configuration测试不需要哦
    //val configuration = new Configuration
//    val uri = new URI("hdfs://192.168.137.190:9000" )
//
//    val fileSystem = FileSystem.get(uri,sc.hadoopConfiguration,"hadoop")
//    if (fileSystem.exists(new Path(args(1)))){
//      fileSystem.delete(new Path(args(1)),true)
//    }

    //统计每个域名的流量和1
//    val lines = sc.textFile("file:///F:/ruozedata_workplace/g5-spark/generatefile")
//    lines.map(x=> {
//      val temp=x.split("\t")
//      //域名前面是hattps//，把后面的截取出来,然后域名后面的用户名以/分割
//      //原数据有的流量会多一个-
//      //注意流量要转为Long类型，不然字符串是拼接的
//      (temp(0).substring(8).split("/")(0),temp(2).replace("-","").toLong)
//    }).reduceByKey(_+_).collect().foreach(println)

    //统计每个域名流量和工业写法2
    val lines = sc.textFile("file:///F:/ruozedata_workplace/g5-spark/generatefile")
//    val lines = sc.textFile(args(0))
//    lines.map(x =>{
//      val temp=x.split("\t")
//      val domain = temp(0).substring(8).split("/")(0)
//      //这里考虑到如果流量还有不标准的数据，会报错出问题，这里先给个默认值
//      var response = 0L
//      //出错就默认值输出错误咯
//      try{
//        response = temp(2).replace("-","").toLong
//      } catch {
//        case exception: Exception =>println("......")
//      }
//      (domain,response)
//    }).reduceByKey(_+_).collect().foreach(println)


    //访问次数最多的url,排序
    //sortBy默认是升序排列
    //val lines = sc.textFile(args(0))
//    lines.map(x=>{
//      val temp = x.split("\t")
//      val url = temp(0)
//      var response = 0L
//      try{
//        response=temp(2).replace("-","").toLong
//      }catch {
//        case exception: Exception => println("!!!!!")
//      }
//      (temp(0),response)
//      //注意take后面加不了save,因为他的返回值是数组,saveastextfile要输入rdd的算子
//      //sortby输入是什么类型，输出就是什么类型
//    }).reduceByKey(_+_).sortBy(_._2,false).saveAsTextFile(args(1))//take(3)//.collect()

    //求每个域名下访问次数最多的URL 排序
    val ac = lines.map(x=>{
      val temp = x.split("\t")
      val url = temp(0)
      val domain =temp(0).substring(8).split("/")(0)
      var response = 0L

      try {
         response = temp(2).replace("-","").toLong
      }catch {
        case exception: Exception=>println("fuckfuckfuck")
      }

      ((domain,url),response)
    }).reduceByKey(_+_)

    //(www.baidu.com,CompactBuffer(((www.baidu.com,https://www.baidu.com/),11)))
    val bc = ac.groupBy(_._1._1)//.foreach(println)

    var dom = bc.map(x=>{
      //注意这里的sortby不是spark的sortby，是scala的，因为输入不是rdd
      val top2 = x._2.toList.sortBy(x => x._2).reverse.take(1)
      top2
    })

    dom.foreach(x=>{
      println(x)
    })


    sc.stop()
  }

}
