package example

import org.apache.log4j._
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object StdWayImplementation extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

//  val ordersDF  = spark.read
//    .format("csv")
//    .option("header",true)
//    .option("inferSchema", true) // Won't use in production
//    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders.csv")
//    .load()


//  Using JSON Read
//
//  val ordersDF  = spark.read
//    .format("json")
////    .option("inferSchema", true) // Won't use in production
////    .option("mode","DROPMALFORMED")
//    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/players.json")
//    .load()


//  Using Parquet FIle Read

  val ordersDF  = spark.read
    //    .option("mode","DROPMALFORMED")
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/users.parquet")
    .load()

  ordersDF.show(false)
  ordersDF.printSchema()
//  import spark.implicits._
//
//  val ordersDataSet = ordersDF.as[OrdersData]
//  ordersDataSet.filter(x => x.order_id > 10).show





  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}
