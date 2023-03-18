package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Session19JoinOperationOnDataFrames extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  // Setting up Spark Conf
  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  // Setting up Spark Session
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

  val customersDF  = spark.read
    .format("csv")
    .option("header",true)
    .option("inferSchema", true)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/customers.csv")
    .load()

  val joinCondition = ordersDF.col("order_customer_id") === customersDF.col("customer_id")
  val joinType = "inner"

  val joinedDF = ordersDF.join(customersDF, joinCondition, joinType).sort("order_customer_id")

  joinedDF.show()


  spark.stop()
}
