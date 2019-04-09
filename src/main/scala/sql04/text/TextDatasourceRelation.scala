package sql04.text

import org.apache.spark.internal.Logging
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SaveMode}

//jdbcrelation
class TextDatasourceRelation(override val sqlContext: SQLContext,
                             path:String,
                             userSchema:StructType)
//日志
  extends BaseRelation
    with TableScan
    with PrunedScan
    with PrunedFilteredScan
    with InsertableRelation
    with Logging {
  //可以用外面传进来StructType，也可以默认
  override def schema: StructType = {
    if (userSchema != null) {
      userSchema
    } else {
      StructType(
        //::和，皆可
        StructField("id",LongType,false)::
        StructField("name",StringType,false)::
          StructField("gender",StringType,false)::
          StructField("salary",LongType,false)::
          StructField("comm",LongType,false)::Nil
      )
    }
  }

  override def buildScan(): RDD[Row] = {

    logWarning("this is ruozedata custom buildScan().")

    //wholeTextFiles,返回前面文件名+后面内容
    val rdd = sqlContext.sparkContext.wholeTextFiles(path).map(x=>x._2)
    //返回的是schema的数组
    val schemaFields = schema.fields

    /*
    把field作用到rdd上面去
    如何根据schema的field的数据类型以及字段顺序整合到rdd
     */

    val rows = rdd.map(fileContent => {
      //注意fileContent是一段内容
      val lines = fileContent.split("\n")
      //转换成seq
      val data = lines.map(x => x.split(",").map(x=>x.trim).toList)

      //按照字段field整合到rdd
      //按照index压在一起
      /**
        * 10000 long
        * ruoze string
        */
      val typedValues = data.map(x => x.zipWithIndex.map {
        case (value, index) => {
          val colName = schemaFields(index).name

          Utils.castTo(if (colName.equalsIgnoreCase("gender")) {
            if (value == "0") {
              "男"
            } else if (value == "1") {
              "女"
            } else {
              "未知"
            }
          } else {
            value
          }, schemaFields(index).dataType)
        }
      })

      typedValues.map(x=>Row.fromSeq(x))
    })

     rows.flatMap(x=>x)

  }

  override def buildScan(requiredColumns: Array[String]): RDD[Row] = {
    logWarning("this is ruozedata custom buildScan(requiredColumns).")

    //wholeTextFiles,返回前面文件名后面内容
    val rdd = sqlContext.sparkContext.wholeTextFiles(path).map(x=>x._2)
    //返回的是schema的数组
    val schemaFields = schema.fields

    /*
    把field作用到rdd上面去
    如何根据schema的field的数据类型以及字段顺序整合到rdd
     */

    val rows = rdd.map(fileContent => {
      //注意fileContent是一段内容
      val lines = fileContent.split("\n")
      val data = lines.map(x => x.split(",").map(x=>x.trim).toSeq)

      //按照字段field整合到rdd
      //按照index压在一起
      /**
        * 10000 long
        * ruoze string
        */
      val typedValues = data.map(x => x.zipWithIndex.map {
        case (value, index) => {
          val colName = schemaFields(index).name

          val castedValue = Utils.castTo(if (colName.equalsIgnoreCase("gender")) {
            if (value == "0") {
              "男"
            } else if (value == "1") {
              "女"
            } else {
              "未知"
            }
          } else {
            value
          }, schemaFields(index).dataType)

          if (requiredColumns.contains(colName)){
            Some(castedValue)
          } else {
            None
          }

        }
      })

      typedValues.map(x=>Row.fromSeq(x.filter(_.isDefined).map(x=>x.get)))
    })

    rows.flatMap(x=>x)
  }

  override def buildScan(requiredColumns: Array[String], filters: Array[Filter]): RDD[Row] = {
    logWarning("this is ruozedata custom buildScan(requiredColumns,filters).")

    logWarning("Fileter:")
    filters.foreach(x => println(x.toString))

    //wholeTextFiles,返回前面文件名后面内容
    val rdd = sqlContext.sparkContext.wholeTextFiles(path).map(x=>x._2)
    //返回的是schema的数组
    val schemaFields = schema.fields

    /*
    把field作用到rdd上面去
    如何根据schema的field的数据类型以及字段顺序整合到rdd
     */

    val rows = rdd.map(fileContent => {
      //注意fileContent是一段内容
      val lines = fileContent.split("\n")
      val data = lines.map(x => x.split(",").map(x=>x.trim).toSeq)

      //按照字段field整合到rdd
      //按照index压在一起
      /**
        * 10000 long
        * ruoze string
        */

      //见下图
      val typedValues = data.map(x => x.zipWithIndex.map {
        //第一列的索引都是0，第二列都是1.。。
        case (value, index) => {
          val colName = schemaFields(index).name

          /**
            * public boolean equalsIgnoreCase(String anotherString)将此 String 与另一个 String 进行比较，不考虑大小写。如果两个字符串的长度相等，并且两个字符串中的相应字符都相等（忽略大小写），则认为这两个字符串是相等的。
            */
            //(value,dataType)
          val castedValue = Utils.castTo(if (colName.equalsIgnoreCase("gender")) {
            if (value == "0") {
              "男"
            } else if (value == "1") {
              "女"
            } else {
              "未知"
            }
          } else {
            value
          }, schemaFields(index).dataType)

          if (requiredColumns.contains(colName)){

            Some(castedValue)
          } else {
            None
          }

        }
      })
      //这个row.fromseq可以通过sequencefile构建一个row
      //isDefined，如果是some类型返回true
      //x把some里的值取出来
      val asd  = typedValues.map(x=>Row.fromSeq(x))
      println(asd)
      typedValues.map(x=>Row.fromSeq(x.filter(_.isDefined).map(x=>x.get)))
    })

    rows.flatMap(x=>x)
  }

  override def insert(data: DataFrame, overwrite: Boolean): Unit = {
    data.write
      .mode(if (overwrite) SaveMode.Overwrite else SaveMode.Append)
      .save(path)
  }
}
