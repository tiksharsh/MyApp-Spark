package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object DFRepartionsExample extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)
  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

  val ordersDF = spark.read.option("header",true)
    .option("inferSchema", true) // Won't use in production
    .csv("/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")
//  ordersDF.show()
//  ordersDF.printSchema()

  val groupOrderDF = ordersDF.repartition(4).where("order_customer_id > 10000")
    .select("order_id", "order_customer_id" )
    .groupBy("order_customer_id")
    .count()

  groupOrderDF.foreach(x => {
    println(x)
  })
  groupOrderDF.show()

  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
//  scala.io.StdIn.readLine()
  spark.stop()

}
