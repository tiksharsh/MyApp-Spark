package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSqlExample extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()


  val ordersDF  = spark.read
    .format("csv")
    .option("header",true)
    .option("inferSchema", true)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/orders.csv")
    .load()

ordersDF.createOrReplaceTempView("orders")
val resultDF = spark.sql("select order_status, count(*) as status_count from orders group by order_status order by status_count desc")

  resultDF.show
  resultDF.printSchema()


  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}
