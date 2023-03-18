package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{expr, sum}

object Session17GroupingAggregations extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .enableHiveSupport()
    .getOrCreate()


  val invoiceDF  = spark.read
    .format("csv")
    .option("header",true)
    .option("inferSchema", true)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/order_data.csv")
    .load()
  // ************************Using Column Object Expression *********************//

  val summaryDF = invoiceDF.groupBy(
    "Country", "InvoiceNo")
    .agg(sum("Quantity").as("TotalQuantity"),
      sum(expr("Quantity * UnitPrice")).as("InvoiceValue")
    )
  summaryDF.show()

  // ************************Above code implementation Using String Expression (2nd Easiest Way)*********************//
  val summaryDF2 = invoiceDF.groupBy("country", "InvoiceNo")
    .agg(expr("sum(Quantity) as TotalQuantity"),
      expr("sum(Quantity * UnitPrice) as InvoiceValue"))
  summaryDF2.show()
  import spark.implicits._
  //    ************************ Above code implementation Using Spark SQL (Most Easiest Way) *********************//
  invoiceDF.createOrReplaceTempView("sales")
  val summaryDF3 = spark.sql(
    """select Country, InvoiceNo,
      |sum(Quantity) as TotalQuantity, sum(Quantity * UnitPrice) as InvoiceValue
      | from sales
      | group by Country, InvoiceNo""".stripMargin)
  summaryDF3.show()

  spark.stop()

}
