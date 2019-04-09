package com.ruozedata.bigdata.scala02

import java.io.{File, PrintWriter}


import scala.io.Source
import scala.util.Random

object makefile{

  val urls = Array("https://www.baidu.com/",
    "https://fenian7788.github.io/",
    "https://me.csdn.net/weixin_39702831",
    "https://blog.csdn.net/umdfml00",
    "https://blog.csdn.net/Jaserok",
    "https://www.jianshu.com/u/32a08e1b7af0",
    "https://blog.csdn.net/Sylvia_D507",
    "https://blog.csdn.net/qq_29416019",
    "https://blog.csdn.net/qq_39892028",
    "https://blog.csdn.net/u010854024",
    "https://blog.csdn.net/qq_34382453",
    "https://blog.csdn.net/weixin_42733888"
  )

  def generateurl()={
    //左闭右开
    urls(Random.nextInt(urls.length))
  }

  val year =Array("2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019")

  def main(args: Array[String]): Unit = {
    //该类可用来创建一个文件并向文本文件写入数据
    val writer = new PrintWriter(new File("generatefile"))

    for (i <- 1 to 10){
      //random.nextint是（0到值）的random
      writer.println(generatefile())
    }
    //注意不能直接关，如果缓存区的数据没有全部写入，会丢失，这里就是强制写完
    writer.flush()
    //关了才能输出在控制台
    writer.close()

    val x = Source.fromFile("generatefile")
    println(x)

    }

  //生成文件每一行拼凑的内容
    def generatefile()={
      //一种string类，效率高但线程不安全，适合单线程
      val fl = new StringBuilder()
      fl.append(generateurl()).append("\t").append(generatetime)
        .append("\t").append(generateliuliang2(30))
     // fl.toString()
    }


    def generateliuliang(range:Int)={
      Random.nextInt(range)
    }

  //造数范围0到9，如果range参数的随机对3的余数为0，则造的数后面加-
    def generateliuliang2(range:Int)={
      if (Random.nextInt(range) % 3 ==0){
        generateliuliang(10).toString + "-"
      }
        else {
        generateliuliang(10)
      }
    }




    def generatetime={
      var date = new StringBuilder()
        date.append(year(Random.nextInt(year.length))).append("-")
      //至少两位整数位，不足补0
        date.append("%02d".format(Random.nextInt(12)+1)).append("-")
        date.append("%02d".format(Random.nextInt(30)+1))
      if (Random.nextInt(30) % 4 ==0){
        date.append("-")
      }
      date
    }


  }


