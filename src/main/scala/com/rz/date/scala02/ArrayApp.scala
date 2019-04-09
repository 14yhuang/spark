package com.rz.date.scala02

import scala.collection.mutable.ArrayBuffer

object ArrayApp {
  def main(args: Array[String]): Unit = {
//定常数组
    val a = new Array[String](5)
    println(a)
//
    a.length
    //赋值,1是索引，从0开始
    a(1)="ruoze"

//
    val b =Array("ruoze","jge")
    b(1)="xxx"


//
//    //,拼接
    b.mkString(",")
    b.mkString("[","&","]")
    println(b)
//
//    //变长数组
//    //要导入包
    import scala.collection.mutable.ArrayBuffer
    val c = ArrayBuffer[Int]()

//
//      //添加1，2
      c += 1
      c += 2

      c += (3,4,5)
      c ++= Array(6,7,8)

//      //第0个位置加0
//      c.insert(0,1)
//    c.foreach(println)
//      //把索引为1的移走
//      c.remove(1)
//        //从第几个位置移几个
//      c.remove(1,3)
//      //移掉最后两个
//      c.trimEnd(2)
//        //把可变数组变为不可变
//      c.toArray
//
//      val c =ArrayBuffer[Int](1,2,3,4)
//      c += 1
//      c.toArray
////两种遍历写法
//      for(i<-0 until c.length){
//          println(c(i))
//      }
//
//      for (ele<-c) {
//          println(ele)
//      }
//
  }
}
