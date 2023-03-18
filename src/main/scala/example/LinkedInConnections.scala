//package example
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.SparkContext
//import org.apache.spark.rdd.RDD
//
//object LinkedInConnections extends App {
//  Logger.getLogger("org").setLevel(Level.ERROR)
//
//  def parseLine(line:String) {
//
//    val fields = line.split(",")
//    val age = fields(2).toInt
//    val numFrnds = fields(3).toInt
//
//    return (age,numFrnds)
//  }
//
//  val sc = new SparkContext("local[*]","wc")
//  val input = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 9/DataSets/friendsdata.data")
//
//  val mappedInput = input.map(parseLine)
//  val mappedFinal = mappedInput.map(x => (x._1, (x._2,1)))
//  val totalByAge = {
//    mappedFinal.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
//  }
//  val avgByAge = totalByAge.map(x => (x._1,x._2._1/x._2._2))
//  avgByAge.collect.foreach {
//    println
//  }
//}
