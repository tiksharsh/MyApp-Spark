package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.{avg, countDistinct, sum}
import org.apache.spark.sql.{SparkSession, functions}

object Session16SimpleAggregations extends App {

    Logger.getLogger("org").setLevel(Level.ERROR)

    val sparkConf = new SparkConf()
    sparkConf.set("spark.app.name", "my-dataframe-example")
    sparkConf.set("spark.master", "local[2]")

    val spark = SparkSession.builder()
      .config(sparkConf)
      .enableHiveSupport()
      .getOrCreate()

// ************************** Reading the file Standard DataFrame Reader API *************************//
    val invoiceDataFrame  = spark.read
      .format("csv")
      .option("header",true)
      .option("inferSchema", true)
      .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/order_data.csv")
      .load()
    invoiceDataFrame.show

    import spark.implicits._
// ************************ Column Object Expression *********************//

    invoiceDataFrame.select(
        functions.count("*").as("Row_Count"),
        sum("Quantity").as("Total_Quantity"),
        avg("UnitPrice").as("Avg_Price"),
        countDistinct("InvoiceNo").as("Count_Distinct")

    ).show()
//    ************************ Above code implementation Using String Expression *********************//

    invoiceDataFrame.selectExpr(
        "count(*) as Row_Count",
        "sum(Quantity) as Total_Quantity",
        "avg(UnitPrice) as Avg_Price",
        "count(Distinct(InvoiceNo)) as Count_Distinct "
    ).show()

//    ************************ Above code implementation Using Spark SQL (Most Easiest Way) *********************//
    invoiceDataFrame.createOrReplaceTempView("sales")
    spark.sql("select count(*) as Row_Count, sum(Quantity) as Total_Quantity, avg(UnitPrice) as Avg_Price, count(distinct(InvoiceNo)) as Count_Distinct from sales").show()
    spark.stop()
}
