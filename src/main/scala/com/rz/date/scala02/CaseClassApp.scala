package com.rz.date.scala02

object CaseClassApp {
  def main(args: Array[String]): Unit = {
    println(Dog)
    println(Dog("wanwan"))
    println(Dog("wanwan").name)
  }
}

case class Dog(name:String)
