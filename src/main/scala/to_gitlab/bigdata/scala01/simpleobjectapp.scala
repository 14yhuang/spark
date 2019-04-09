package com.ruozedata.bigdata.scala01

object simpleobjectapp {
  def main(args: Array[String]): Unit = {

    val people = new People
    people.name = "kaka"
    println(people.name +":"+ people.age)

    println(people.code())
    people.asd
  }
}

class People{
  //var name:String =_  占位符
  /*scala> var name:String =_
    name: String = null

    scala> var name=""
    name: String = ""

    scala> var name:Int =_
    name: Int = 0
   */
  var name=""
  var age=10

  //私有属性
  private[this] val gender="male"

  def code()={
    name +"coding"
  }

  def asd()={
    println(name+"asd")
  }
}