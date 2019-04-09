package com.ruozedata.bigdata.core4

import org.apache.spark.{SparkConf, SparkContext}

object BroadcastApp {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local[2]").setAppName("BroadcastApp")

    val sc = new SparkContext(sparkConf)

    broadcastJoin(sc)
    //commonJoin(sc).foreach(println)


    sc.stop()
  }

  //让g5 join f11 on g5.id = f11.id => (34,"进取","深圳", 18)
  def commonJoin(sc:SparkContext)={
    val g5 = sc.parallelize(Array(("1", "豆豆"), ("2", "牛哥"), ("34", "进取"))).map(x=>(x._1,x))
    val f11 = sc.parallelize(
      Array(("34", "深圳", 18), ("10", "北京", 2))
    ).map(x => (x._1,x))

    //注意要join需要数据是key-value的形式，所以上面的要做类型转换
    //join后的数据结构rdd.RDD[(String, ((String, String), (String, String, Int)))]
    g5.join(f11).map(x => {
      x._1+","+ x._2._1._2 + "," + x._2._2._2 + "," + x._2._2._3
    }).collect()


  }

  def broadcastJoin(sc:SparkContext)={

    //假设g5是小表,collectAsMap返回的是key-value的键值对的map形式(方便得到key),返回到driver端,存于内存，不能过大
    /**
      * key和value分别是
      * Set(2, 1, 34)
      * HashMap(牛哥, 豆豆, 进取)
      */
    val g5 = sc.parallelize(Array(("1", "豆豆"), ("2", "牛哥"), ("34", "进取"))).collectAsMap()

    //广播小表
    /**id和value
      * 1
      * Map(2 -> 牛哥, 1 -> 豆豆, 34 -> 进取)
      */
    val g5Broadcast = sc.broadcast(g5)
//    println(g5.keys)
//    println(g5.values)
//    println(g5Broadcast.id)
//    println(g5Broadcast.value)
    //sc.longAccumulator()

    //f11
    val f11 = sc.parallelize(Array(("34", "深圳", 18), ("10", "北京", 2))).map(x => (x._1, x))

    f11.mapPartitions(partition => {
      val g5student = g5Broadcast.value
      //contains是判断map里的这个key是否有绑定，就是有值
      //针对每一次 for 循环的迭代, yield 会产生一个值，被循环记录下来 (内部实现上，像是一个缓冲区).
      // 当循环结束后, 会返回所有 yield 的值组成的集合.返回集合的类型与被遍历的集合类型是一致的
      for ((key,value) <- partition if (g5student.contains(key)))
        yield(key,g5student.getOrElse(key,""),value._2)

    }
    )

  }






}
