package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ReadFileAsSparkContextExample extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  val myRegex = """^(\S+) (\S+)\t(\S+)\,(\S+)""".r

  case class Order(order_id : Int, customer_id : Int, order_status: String)

  def parser(line: String) = {
    line match {
      case myRegex(order_id, date, customer_id, order_status) =>
        Order(order_id.toInt, customer_id.toInt, order_status)
    }
  }

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .getOrCreate()

  val lines = spark.sparkContext.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/orders_new.csv")

  import spark.implicits._

  val ordersDS = lines.map(parser).toDS().cache()

  ordersDS.printSchema()
  ordersDS.select("order_id","customer_id", "order_status").show()
  ordersDS.groupBy("order_status").count().show()


  Logger.getLogger(getClass.getName).info("My Application Completed Successfully")
  //  scala.io.StdIn.readLine()
  spark.stop()

}

