package com.ruozedata.bigdata.scala03

object AdFunctionApp {
  def main(args: Array[String]): Unit = {
    val l = List(1,2,3,4)

//    列表里面的元素*2
//    for(ele <- l){
//      println(ele*2)
//    }
//
//    对列表中每一个元素都做一个操作
//    println(l.map((x:Int) => x*2))
//    println(l.map(x => x*2))
//    println(l.map(_ * 2))
//
//    println换行，print不换行
//    l.map(_*2).foreach(x => println(x))
//    l.foreach( x => println(x*2))
//
//    筛选咯
//    println(l.map(_*2).filter(_ >8))
//
//    取前3个
//    println(l.take(3))
//
//    求和
//    println(l.reduce(_+_))
//    1-2-3-4
//    println(l.reduce(_-_))
//
//    用下面代码试试是什么意思
//    println(l.reduceLeft(_-_))
//    println(l.reduceRight(_-_))
//
//    1,2
//    -1,3
//    -4,4
    l.reduceLeft((x,y) => {
      println(x+","+y)
      x-y
    })
//
//
//    3,4
//    2,-1
//    1,3
    l.reduceRight((x,y) => {
      println(x+","+y)
      x-y
    })
//
//    curry
//    2相当于初始值，最后加上后面的结果
//    println(l.fold(2)(_+_))
//    相减看下面代码，结果为0
//    println(l.fold(10)(_-_))
//
//    10,1
//    9,2
//    7,3
//    4,4
//    l.fold(10)((x,y) =>{
//      println(x+","+y)
//      x-y
//    })
//
//    里面要有判断条件
    println(l.count(_>1))
//
//
//    扁平化操作
//    val f=List(List(1,2),List(3,4))
//    List(List(1, 2), List(3, 4))
//    println(f)
//    List(1, 2, 3, 4)
//    println(f.flatten)
//    List(2, 4, 6, 8)
//    println(f.flatMap(_.map(_*2)))


  }

//  def sum(a:Int,b:Int)=a+b
//  curry
//  def add(a:Int)(b:Int)=a+b
}
