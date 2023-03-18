package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{column, expr}

object SqlSelectCols extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .enableHiveSupport()
    .getOrCreate()


  val ordersDF  = spark.read
    .format("csv")
    .option("header",true)
    .option("inferSchema", true)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/orders.csv")
    .load()

  import spark.implicits._
  ordersDF.select("order_id","order_status").show()
  ordersDF.select($"order_date", 'order_status).show()
  ordersDF.select(column("order_date"), expr("concat(order_status, '_STATUS')")).show()
}
