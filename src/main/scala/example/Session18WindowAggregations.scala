package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.sum

object Session18WindowAggregations extends App {
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
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/windowdata.csv")
    .load()

  val myWindow = Window.partitionBy("Country")
    .orderBy("weeknum")
    .rowsBetween(Window.unboundedPreceding, Window.currentRow)

  val myDF = invoiceDF.withColumn("RunningTotal",
    sum("invoicevalue").over(myWindow))
  myDF.show()

  spark.stop()



}
