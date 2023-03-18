package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
//import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType, TimestampType}
import org.apache.spark.sql.types._

object ExplicitSchemaProgramaticallyExample extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

  val ordersSchema = StructType(List(
    StructField("orderid",IntegerType),
    StructField("orderdate",TimestampType),
    StructField("customerid",IntegerType),
    StructField("status",StringType)

  ))
    val ordersDF  = spark.read
      .format("csv")
      .option("header",true)
      .schema(ordersSchema)
      .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")
      .load()




  ordersDF.show()
  ordersDF.printSchema()
  //  import spark.implicits._
  //
  //  val ordersDataSet = ordersDF.as[OrdersData]
  //  ordersDataSet.filter(x => x.order_id > 10).show





  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}

