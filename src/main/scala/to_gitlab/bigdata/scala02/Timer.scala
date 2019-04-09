package com.ruozedata.bigdata.scala02

object Timer {
  var index =0

  def incr() ={
    index = index +1
    index
  }

  //从结果可知，object就像是静态，动态在子程序中，每次调用都会从它的初始值开始调用
  //静态变量会从变化后的值继续改变
  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10){
      println(incr())
    }


  }
}
