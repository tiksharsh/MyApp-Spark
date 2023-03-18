package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}

object StructureAPI2SinkExample extends App {

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



  print ("OrdersDF has "+ordersDF.rdd.getNumPartitions)

  val ordersRepartitions = ordersDF.repartition(4)
  print ("\n OrderRepartions has " +ordersRepartitions.rdd.getNumPartitions)

  ordersRepartitions.write
    .format("csv")
    .partitionBy("order_status")
    .mode(SaveMode.Overwrite)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/outputFolder")
    .save()

  ordersRepartitions.printSchema()


  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}
