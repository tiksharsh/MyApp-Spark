package example
import org.apache.spark.SparkContext

object RatingCalculator extends App {
//  Logger.getLogger("org").setLevel(Level.ERROR)
  //  def main(args: Array[String]): Unit = {
  val sc = new SparkContext("local[*]", "wc")
  val input = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 9/DataSets/moviedata.data")
  val mapInput = input.map(x => x.split("\t")(2))
  val ratings = mapInput.map(x => (x,1))
  val reducedRatings = ratings.reduceByKey((x,y) => x+y)
  val results = reducedRatings.collect
  results.foreach(println)
}
