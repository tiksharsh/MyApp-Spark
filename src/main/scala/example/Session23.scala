package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Session23 extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)

  case class Logging(level : String, datetime: String)

  def mapper(line : String): Logging = {
    val fields = line.split(',')
    val logging: Logging = Logging(fields(0), fields(1))
    return logging
  }

  // Setting up Spark Conf
  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  // Setting up Spark Session
  val spark = SparkSession.builder()
    .config(sparkConf)
    .enableHiveSupport()
    .getOrCreate()

  val myList = List("DEBUG,2015-2-6 16:24:07",
  "WARN,2016-7-26 18:54:43",
  "INFO,2012-10-18 14:35:19",
  "DEBUG,2012-4-26 14:26:50",
  "DEBUG,2013-9-28 20:27:13",
  "INFO,2017-8-20 13:17:27")

  import spark.implicits._
//********************* Example with manual created List ******************//
/*  val rdd = spark.sparkContext.parallelize(myList)

  val rdd2 = rdd.map(mapper).cache()
  val df1 = rdd2.toDF()
  df1.show()

  df1.createOrReplaceTempView("logging_table")
//  spark.sql("select * from logging_table").show()
//  spark.sql("select level, collect_list(datetime) as datetime2 from logging_table group by level order by level").show(false)
  val resultDF = spark.sql("select level, date_format(datetime, 'MMMM') as month from logging_table")
  resultDF.createOrReplaceTempView("new_logging_table")
  spark.sql("select level, month, count(1) from new_logging_table group by level, month").show()
*/

  //********************* Example with actual File ******************//

  val fileDF = spark.read.option("header", true).csv("/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/biglog.txt")
  fileDF.createOrReplaceTempView("my_new_logging_table")

//  val result = spark.sql("select level, date_format(datetime, 'MMMM') as month, count(1) as total from my_new_logging_table group by level, month")
//  result.createOrReplaceTempView("RESULT_TABLE")
//  spark.sql("select level, date_format(datetime, 'MMMM') as month, cast(date_format(datetime, 'M') as int) as monthnum from my_new_logging_table").groupBy("level").pivot("monthnum").count().show(100)

  val columns = List("January", "February","March", "April", "May", "June", "July","August", "September","October", "November","December")
  spark.sql("select level, date_format(datetime, 'MMMM') as month from my_new_logging_table").groupBy("level").pivot("month", columns).count().show(100)

  spark.stop()
}
