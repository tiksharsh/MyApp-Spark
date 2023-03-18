package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{column, monotonically_increasing_id, unix_timestamp}
import org.apache.spark.sql.types.DateType

object Session15UseCaseImplement extends App {

    Logger.getLogger("org").setLevel(Level.ERROR)



    val sparkConf = new SparkConf()
    sparkConf.set("spark.app.name", "my-dataframe-example")
    sparkConf.set("spark.master", "local[2]")

    val spark = SparkSession.builder()
      .config(sparkConf)
      .enableHiveSupport()
      .getOrCreate()

  val myList = List(
    (1, "2013-07-25", 11599, "CLOSED"),
    (2, "2013-07-25", 256, "PENDING_PAYMENT"),
    (3, "2013-07-25", 8999, "COMPLETE"),
    (4, "2013-07-25", 1, "CLOSED"),
    (5, "2013-07-25", 32, "DONE"),
    (6, "2013-07-25", 11599, "CLOSED")
  )
  import spark.implicits._
/*  val rddList = spark.sparkContext.parallelize(myList)
  rddList.toDF()
 */

// Alternate Easier Way
  val ordersDataFrame = spark.createDataFrame(myList)
    .toDF("order_id", "order_date", "customer_id", "status")
  ordersDataFrame.printSchema()
  ordersDataFrame.show()
  val newDF = ordersDataFrame
    .withColumn("order_date", unix_timestamp(column("order_date")
      .cast(DateType)))                                             // order_date column format changed
    .withColumn("new_id", monotonically_increasing_id()) // added new column new_id
    .dropDuplicates("order_date", "customer_id") // Dropped duplicates in a column order_date and customer_date
    .drop("order_id") // Dropped Column order_id
    .sort("new_id") // Sort on new_id column

  newDF.printSchema()
  newDF.show()



}
