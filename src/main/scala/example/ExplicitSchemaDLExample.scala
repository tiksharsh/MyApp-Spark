package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp
import org.apache.spark.sql.types._

object ExplicitSchemaDLExample extends App {

  case class OrdersData (order_id: Int, order_date: Timestamp, order_customer_id: Int, order_status: String)

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

 /* val ordersSchema = StructType(List(
    StructField("orderid",IntegerType),
    StructField("orderdate",TimestampType),
    StructField("customerid",IntegerType),
    StructField("status",StringType)

  )) */
  val orderSchemaDL = "orderid Int, orderdate String, custid Int, orderstatus String"
  val ordersDF  = spark.read
    .format("csv")
    .option("header",true)
    .schema(orderSchemaDL)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")
    .load()

  import spark.implicits._
  val ordersDS = ordersDF.as[OrdersData]
   ordersDS.filter(x => x.order_id > 1000).show

  ordersDS.printSchema()


  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}
