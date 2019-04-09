package sql04.app

import org.apache.spark.sql.SparkSession

object CustomTextApp {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("CustomTextApp")
      .master("local[2]")
      .getOrCreate()

    import sparkSession.implicits._

    //会自动找DefaultSource
    val people = sparkSession.read.format("sql04.text").option("path","file:///F:\\ruozedata_workplace\\g5-spark\\jdbc.txt").load()
    people.printSchema()

    //val person = sparkSession.read.text("file:///F:/ruozedata_workplace/g5-spark/jdbc.txt").show()
//  //  people.select("id","name").filter('id>2).filter('salary >2000).show()
//    people.show()
    //people.select("id","name").write.format("sql04.text").save("file:///F:\\ruozedata_workplace\\g5-spark\\jdbc_write")



//    val people = sparkSession.read.text("file:///F:\\ruozedata_workplace\\g5-spark\\jdbc.txt")
//    people.show()

    sparkSession.stop()
  }
}
