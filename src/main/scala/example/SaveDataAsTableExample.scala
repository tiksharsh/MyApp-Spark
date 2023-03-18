package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object SaveDataAsTableExample extends App {

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



  print ("OrdersDF has "+ordersDF.rdd.getNumPartitions)

  val ordersRepartition = ordersDF.repartition(1)
  print ("\n OrderRepartion has " +ordersRepartition.rdd.getNumPartitions)

  spark.sql("create database if not exists retailDB")

  ordersRepartition.write
    .format("csv")
//    .partitionBy("order_status")
    .mode(SaveMode.Overwrite)
    .bucketBy(2,"order_customer_id")
    .sortBy("order_customer_id")
    .saveAsTable("retailDB.OrdersTbl")

  spark.catalog.listTables("retailDB").show

  ordersRepartition.printSchema()


  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}

