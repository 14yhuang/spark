package com.ruozedata.bigdata.core02

import org.apache.spark.{SparkConf, SparkContext}

object Spark02 {
  def main(args: Array[String]): Unit = {
    val sparkConf=new SparkConf()
      //在spark-shell运行的时候再指定，这里的优先级要比在shell里更高，appname可以默认
      //shell里有内置
      .setMaster("local[2]").setAppName("Spark02")

    val sc =new SparkContext(sparkConf)

    //创建rdd方法1，第一个参数是数组，第二个参数是默认切片
    val rdd1 = sc.parallelize(Array(1,2,3,4,5))
//    //collect把rdd里所有元素以数组方式返回
    rdd1.collect().foreach(println)

    //文件数据创建rdd,要保证每个节点下面相同路径有相同文件，返回的是一个字符串的rdd
    //第一个参数路径，第二个参数是最小分区数，分区数会影响到最终存储时生成文件的个数
    //读本地文件，建议加file声明
    //foreach是个action
    val rdd = sc.textFile("file:///E:/新建文件夹/ruoze wenjian shiping/asd.txt")
    rdd.collect().foreach(println)
//    //注意top和collect都会内存加载所有数据
    rdd.top(3)
//    //默认升序，第一个参数是函数，第二个参数是排序方法
    rdd.sortBy(x=>x)
//
//    //存储文件
    rdd.saveAsTextFile("file:///E:/新建文件夹/ruoze wenjian shiping/asd.txt")

    sc.stop()
  }

}
