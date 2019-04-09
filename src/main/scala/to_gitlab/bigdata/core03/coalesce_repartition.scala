package com.ruozedata.bigdata.core03

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object coalesce_repartition {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf()
      .setMaster("local[2]").setAppName("ForeachPartitionApp")
    val sc = new SparkContext(sparkConf)

    val students = new ListBuffer[String]()
    for(i <- 1 to 100) {
      students += "若泽数据实战班五期：" + i
    }

    val stus = sc.parallelize(students, 3)
    //mapPartitionsWithIndex返回索引和partition
    stus.mapPartitionsWithIndex((index, partition) => {
      val emps = new ListBuffer[String]

      //分区是迭代的，判断还有没有元素
      while(partition.hasNext) {
        emps += ("~~~" + partition.next() + " , 原部门:[" + (index+1) + "]")
      }
      emps.iterator
    }).foreach(println)

//    println("============华丽的分割线================")
    stus.coalesce(2).mapPartitionsWithIndex((index, partition) => {
      val emps = new ListBuffer[String]
      while(partition.hasNext) {
        emps += ("~~~" + partition.next() + " , 新部门:[" + (index+1) + "]")
      }
      emps.iterator
    }).foreach(println)
//    println("============华丽的分割线================")
//
//    stus.repartition(5).mapPartitionsWithIndex((index, partition) => {
//      val emps = new ListBuffer[String]
//      while(partition.hasNext) {
//        emps += ("~~~" + partition.next() + " , 新部门:[" + (index+1) + "]")
//      }
//      emps.iterator
//    }).foreach(println)

    sc.stop()
  }

}
