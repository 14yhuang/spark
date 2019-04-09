package com.ruozedata.bigdata.scala02

object OptionApp {
  def main(args: Array[String]): Unit = {
    val m =Map(1->2)
    println(m(1))
    //返回Some(2)
    println(m.get(1))
    //None
    println(m.get(2))
  //返回2
    println(m.get(1).get)
    //如果用get，原先是None，会报错
    println(m.get(2).getOrElse(0))

  }
}
