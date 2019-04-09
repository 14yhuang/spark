package com.ruozedata.bigdata.ml

import org.apache.spark.ml.linalg.{Matrix, Vectors}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.ml.stat.Correlation


object first {
  def main(args: Array[String]): Unit = {

    val session  = SparkSession.builder()
      .master("local[2]")
      .appName("ml")
      .getOrCreate()

    import session.implicits._

    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )

    val df = data.map(Tuple1.apply).toDF("features")
    val Row(coeff1: Matrix) = Correlation.corr(df, "features").head
    println(s"Pearson correlation matrix:\n $coeff1")

    val Row(coeff2: Matrix) = Correlation.corr(df, "features", "spearman").head
    println(s"Spearman correlation matrix:\n $coeff2")

    session.stop()
  }
}
