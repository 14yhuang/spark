package com.ruozedata.bigdata.scala03

object StringApp {
  def main(args: Array[String]): Unit = {
    val name  ="asd"
    println("hello:"+name)
    //字符串插值
    println(s"hello:$name")

    //""""""好多输出
    val b =
      s"""
        |huanying asd
        |fuck you
        |$name asd
      """.stripMargin

    println(b)
  }
}
