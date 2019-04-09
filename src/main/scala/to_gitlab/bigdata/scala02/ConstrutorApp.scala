package com.ruozedata.bigdata.scala02

object ConstrutorApp {
  def main(args: Array[String]): Unit = {
//    val person = new Person("asd",30)
//
//    println(person.name + " , " + person.age + " , " + person.school)

    val person2 = new Person("J总",18,gender = "asd")
    println(person2.name + "," + person2.age + "," + person2.school + "," + person2.gender)

//      val student = new Student(name = "xiaodoudou",age = 12,major = "student")
//      println(student.name + ","  + student.age + "," +student.major + "," +student.kkk())
    }


}

class Person(val name:String,  var age:Int){
  println("---ru---")

  var school = "ustc"
  val xxx ="xxx"
  var gender:String = "nv"

  println("--leave--")

  def this(name:String,age:Int,gender:String){

    this(name,age)
    println("asd")
    this.gender=gender
    this.school="stu"
  }

  def this(name: String,age:Int,gender:String,school:String){

    this(name,age,gender)
    this school=school
    println("ktv")
  }
}

class Student(name:String, age:Int  ,val major:String) extends Person(name,age){
  println("---Student Leave---")

   override def toString() ="student tostring"

  override val xxx: String = "kkk"
   def kkk()="fuck"

   val english = "bad"

  println("--student leave--")
}

//主构造器和附属构造器范围
//附属构造器调用附属构造器，两个this？
//集体注释快捷 ctrl /
//