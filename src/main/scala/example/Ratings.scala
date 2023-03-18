package example

import org.apache.spark.SparkContext

object Ratings extends App {
  //  Logger.getLogger("org").setLevel(Level.ERROR)
  //  def main(args: Array[String]): Unit = {
  val sc = new SparkContext("local[*]", "wc")
  val ratingsRDD = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 11/DataSets/ratings.dat")

  val mappedRDD = ratingsRDD.map(x => {
    val fields = x.split("::")
    (fields(1),fields(2))
  })

  val newMapped = mappedRDD.mapValues(x => (x.toFloat,1.0))
  val reducedRatings = newMapped.reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))

  val filteredRDD = reducedRatings.filter(x => x._2._2 > 1000)
  val ratingsProcessed = filteredRDD.mapValues(x => x._1/x._2).filter(x => x._2 > 4.0)

  ratingsProcessed.collect.foreach(println)

}