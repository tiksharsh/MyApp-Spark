package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object LogLevelByGrouping extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
  val sc = new SparkContext("local[*]","word-count")
  val baserdd = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 10/DataSets/bigLog.txt")

  val mappedRdd = baserdd.map(x => {
    (x.split(":")(0),x.split(":")(1))
//    val fields = x.split(":")(fields(0),fields(1))
  })
  mappedRdd.groupByKey.collect().foreach(x => println(x._1, x._2.size))
  scala.io.StdIn.readLine()
}
