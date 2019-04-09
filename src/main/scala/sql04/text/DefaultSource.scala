package sql04.text

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.sources.{BaseRelation, RelationProvider, SchemaRelationProvider}
import org.apache.spark.sql.types.StructType

//jdbcrelationprovider
//必须叫DefaultSource，不然调用报错
//schemarelationprovider可以让用户传入schema
class DefaultSource extends RelationProvider  with SchemaRelationProvider{

  def createRelation(
                      sqlContext: SQLContext,
                      parameters: Map[String, String],
                      schema: StructType): BaseRelation = {
    //读文件就要path
    val path = parameters.get("path")
    path match {
      case Some(x) => new TextDatasourceRelation(sqlContext, x, schema)
      case _ => throw new IllegalArgumentException("path is required for custom-text-datasource-api")

    }
  }


  override def createRelation(sqlContext: SQLContext, parameters: Map[String, String]): BaseRelation = {
        createRelation(sqlContext,parameters,null)

  }
}
