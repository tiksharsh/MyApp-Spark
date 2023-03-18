package example

import org.apache.spark.SparkContext
import scala.io.Source

object RealTimeSparkExample extends App {


  def loadingBoaringWords(): Set[String] = {

    var boarning_words: Set[String] = Set()
    val lines =  Source.fromFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 10/DataSets/boringwords.txt").getLines()

    for (line <- lines){
      boarning_words += line
    }
    boarning_words
  }

  val sc = new SparkContext("local[*]","wc")
  var nameSet  = sc.broadcast(loadingBoaringWords)

  val initial_rdd = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 10/DataSets/bigdatacampaigndata.csv")

  val mapped_input = {
    initial_rdd.map(x => (x.split(",")(10).toFloat, x.split(",")(0)))
  }
  val words = mapped_input.flatMapValues(x => x.split(" "))
  val finalMapped = words.map(x => (x._2.toLowerCase(),x._1))

//  Filter Out Words here
  val filteredRDD = finalMapped.filter(x => !nameSet.value(x._1))

  val total = filteredRDD.reduceByKey((x, y) => x+y)
  val sorted = total.sortBy(x => x._2, false)
  sorted.take(20).foreach(println)
}
