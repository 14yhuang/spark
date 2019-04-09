package com.ruozedata.bigdata.scala02

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

object WordCount {

  def main(args: Array[String]): Unit = {

    var file = Source.fromFile("F:\\asd.txt")
    var first = ArrayBuffer[String]()

    //返回List(asd asd asd, s d f g s, a)，不加tolist就是个生成器，其实和下面循环做法是一样的
//    print(file.getLines().toList)


    for (line <- file.getLines()){

       first += line
     //  println(first)
    }

//    println(first)
    var second = first.flatMap(_.split(" "))
   // println(second)
    //这里用map返回的是个地址
//    var second2 = first.map(_.split((" ")))
//    println(second)
//    println(second2)

    var third = second.map((_,1)).groupBy(_._1).mapValues(_.size).toList
   // println(third)



  }

}