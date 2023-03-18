package example

import org.apache.spark.SparkContext
import org.apache.log4j.Level
import org.apache.log4j.Logger

object WordCount extends App {

//  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]","wc")
    val input = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 9/DataSets/search_data.txt")
    val words = input.flatMap(x => x.split(" "))
    val wordsLower = words.map(x => x.toLowerCase())
    val wordMap = wordsLower.map( x => (x,1))
    val finalCount = wordMap.reduceByKey((a,b) => a+b)
    val reversedTuple = finalCount.map(x => (x._2,x._1))
    val sortedResults = reversedTuple.sortByKey(false).map(x => (x._2,x._1))
//    sortedResults.collect.foreach(println)
    val results = sortedResults.collect

      for(result <- results) {
        val word = result._1
        val count = result._2
        println(s"$word : $count")
      }
    println {
      "harsh"
   }

//  }
    scala.io.StdIn.readLine()

}
