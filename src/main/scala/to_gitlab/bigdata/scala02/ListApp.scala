package com.ruozedata.bigdata.scala02

import scala.collection.mutable.ListBuffer

object ListApp {
//  //Nil可以生成个不可变的集合
//  var a = Nil
//  //正常集合构建方法
def main(args: Array[String]): Unit = {
  val l =List(1,2,3,4,5)

  println(l.tail)
}

//
//  //list 只有头和尾，尾是除了第一个元素外的其他元素
//  l.head

//
//  //1拼接一个空的集合就是只有1的集合
//  val l2 = 1::Nil
//  val l3= 2::l2
//
//  //可变集合
//  import scala.collection.mutable.ListBuffer
//  val l4 =ListBuffer[Int]()
//
//  l4 ++= List(1,2,3)
////虽然没有4，但是也不会报错
//  l4 -= (1,4)
//  l4 --= l4(2,3)
//
//  l4.toArray
//  l4.toList
//
//  l4.isEmpty
//
//  def sum(nums:Int*):Int= {
//    if (nums.length == 0) {
//      0
//    } else {
//        //自己调用自己，变类型
//      nums.head + sum(nums.tail: _*)
//    }
//
//  }
//
//
}
