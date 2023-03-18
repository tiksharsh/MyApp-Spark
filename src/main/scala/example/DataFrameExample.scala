package example

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DataFrameExample extends App {

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
  .config(sparkConf)
    .getOrCreate()

  val ordersDF = spark.read.option("header",true)
    .option("inferSchema", true) // Won't use in production
    .csv("/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")
  ordersDF.show()
  ordersDF.printSchema()
  scala.io.StdIn.readLine()
  spark.stop()

}
