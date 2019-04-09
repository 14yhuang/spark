package com.ruozedata.bigdata.scala03

import scala.util.Random



object MatchApp {
  def main(args: Array[String]): Unit = {

    val teachers = Array("qwe","asd","zxc")
    //随机选一个
    val teacher=teachers(Random.nextInt(teachers.length))

//    for(i <- 1.to(4)){
//      //随机选一个
//      val teacher=teachers(Random.nextInt(teachers.length))
//      println(teacher)
//    }

    //模式匹配
//    teacher match {
//      case "qwe"=> println("ewq")
//      case "asd"=> println("dsa")
//      case "zxc"=> println("cxz")
//    }

    //偏函数
    def saysomething(name:String)= name match {
      case "qwe"=> println("ewq")
      case "asd"=> println("dsa")
      case "zxc"=> println("cxz")
    }

    saysomething("qwe")
//进去是个字符串类型，出来也是字符串类型
    //模式匹配和偏函数，区别，被包在花括号没有match的一组case语句是偏函数
    def saysomething2:PartialFunction[String,String]={
      case "qwe"=> "ewq"
      case "asd"=> "dsa"
      case "zxc"=> "cxz"
    }

    println(saysomething2("asd"))



    def greeting(array: Array[String])={
      array match {
        case Array("zhangsan") => println("hi:zhangsan")
        case Array(x,y)=> println(s"hi:$x,$y")
        case Array("zhangsan",_*) => println("hi asd")
        case _=>println("welcome..")

      }
    }

    greeting(Array("zhangsan","asd"))

    try{
          val i=1/0
          println(i)
        }catch {
      //Ar毕竟是Exception的子类，但是如果写的更前，报Ar
          case e:Exception => println("aaaa")
          case e:ArithmeticException =>println("cuola")
        }finally {
          println("这个finall是一定要执行的")
        }

  }
}
