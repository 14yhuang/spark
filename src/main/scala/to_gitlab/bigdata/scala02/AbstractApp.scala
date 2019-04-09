package com.ruozedata.bigdata.scala02

object AbstractApp {
  def main(args: Array[String]): Unit = {
    //抽象类不能new
    val s = new Person2 {
      override def hit: Unit = "asd"

      override val name: String = "asd"
    }
    println(s.name)

  }
}

abstract class Person2{
  def hit
  val name:String
}

//抽象都要实现
class Student2 extends Person2{
  override def hit: Unit = {
    println("asd hit asd")
  }

  override val name: String = "xxx"
}