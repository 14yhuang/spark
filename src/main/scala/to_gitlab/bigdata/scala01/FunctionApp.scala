package com.ruozedata.bigdata.scala01

object FunctionApp {
  def main(args: Array[String]): Unit = {
    println(add3(3,5))
    sayhello()
    sayhello
    //可以不传参数，但是不能不加（）
    sayname()
//数组遍历,作用一个函数
    Array("asd","sd").foreach(x=>println(x))
//赋值<-
    for ( x <- 1 to 10 if(x%2==0)){
      println(x
      )

    }
    println(speed(10,1))
    sum(1,2,3,4)
//1 to 10 是range类型,_*变为合适类型
    println(sum(1 to 10 : _*))


  }

  //默认参数
  def sayname(name:String="edc")={
    println(name)
  }

  //命名参数
  def speed(distance:Float,time:Float)={
    distance/time

  }

  //变长参数
  def sum(a:Int*)={
    var result =0
    for (ele <- a){
      result += ele
    }
    result
  }


  def add(x:Int,y:Int):Long={
    x+y
  }

  def add3(x:Int,y:Int)=x+y

  //没有入参可以直接sayhello
  def sayhello()={
    println("asd")
  }
/*
  scala> 1 to 10  /  1.to(10)
  res13: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  scala> 1 until 10   /1.until(10)
  res14: scala.collection.immutable.Range = Range(1, 2, 3, 4, 5, 6, 7, 8, 9)

  scala> Range(1,10)
  res16: scala.collection.immutable.Range = Range(1, 2, 3, 4, 5, 6, 7, 8, 9)
*/


}
