package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object UserDefinedFunctionExample extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  case class Person(name : String, age : Int, city: String )


  def ageCheck(age : Int) = {
    if(age > 18) "Y" else "N"
  }

  val sparkConf = new SparkConf()
  sparkConf.set("spark.app.name", "my-dataframe-example")
  sparkConf.set("spark.master", "local[2]")

  val spark = SparkSession.builder()
    .config(sparkConf)
    .enableHiveSupport()
    .getOrCreate()


  val ordersDF  = spark.read
    .format("csv")
//    .option("header",true)
    .option("inferSchema", true)
    .option("path","/Users/Wolverine/Documents/BigData-Hadoop/Week 12/DataSets/dataset1")
    .load()

  import spark.implicits._
  val df = ordersDF.toDF("name","age","city")
  df.printSchema()

  df.show()

 /* val parseAgeFunctions = udf(ageCheck(_: Int):String)
  val DF2 = df.withColumn("adult", parseAgeFunctions(column("age")))

  DF2.show() */
  //  ***************** Using Column Object Expression ***********************//

  spark.udf.register("parseAgeFunctions", ageCheck(_: Int):String)
  // spark.udf.register("parseAgeFunctions", (x:Int) => if (x > 18) "Y" else "N") // Written anonymous function here but it works like above line
  val DF = df.withColumn("adult", expr("parseAgeFunctions(age)"))
  DF.show()
  spark.catalog.listFunctions().filter(x => x.name == "parseAgeFunctions").show

//  ***************** Using SQL Expression ***********************// This is most easier way
  df.createOrReplaceTempView("peopletable")
  spark.sql("select name, age, city, parseAgeFunctions(age) as adult from peopletable").show



//  ***************** DataFrame to DataSet and DataSet to DataFrame Conversion ***********************//

//  val DS = df.as[Person]
//
//  val dsTodf = DS.toDF().as[Person].toDF()



}
