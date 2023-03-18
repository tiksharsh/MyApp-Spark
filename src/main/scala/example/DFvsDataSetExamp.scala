package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.execution.streaming.FileStreamSource.Timestamp
import org.apache.spark.sql.{Dataset, Row, SparkSession}

case class OrdersData (order_id: Int, order_date: Timestamp, order_customer_id: Int, order_status: String)

object DFvsDataSetExamp extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

  val ordersDF : Dataset[Row]  = spark.read
    .option("header",true)
    .option("inferSchema", true) // Won't use in production
    .csv("/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")

  //    ordersDF.filter("order_ids > 10000").show
  //    ordersDF.show()
  //  ordersDF.printSchema()


    import spark.implicits._

    val ordersDataSet = ordersDF.as[OrdersData]
    ordersDataSet.filter(x => x.order_id > 10).show





  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()
}
