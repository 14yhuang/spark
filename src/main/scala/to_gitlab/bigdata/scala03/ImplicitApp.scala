package com.ruozedata.bigdata.scala03

import scala.io.Source
//导入java的File类
import java.io.File

import ImplicitAspect._

object ImplicitApp {
  def main(args: Array[String]): Unit = {
    //隐式转换,传进来个普通对象，赋予他高级对象的功能
    implicit def man2superman(man: Man):Superman=new Superman(man.name,man.name)
    val man =new Man("xxx")
    //如果原函数有fly方法，优先使用原函数的
    man.fly()

//    implicit def file2RedFile(file:File):RichFile=new RichFile(file)
//    val file = new File("f:/asd.txt")
//    val content =file.read()
//    println(content)

  }
}

class Man(var name:String){
//  def fly()={
//    println("asd")
//  }
}

class Superman(var name: String,var name2:String){
  def fly()={
    println("niubi")
  }
}

class RichFile(val file:File){
  def read() =Source.fromFile(file.getPath).mkString
}