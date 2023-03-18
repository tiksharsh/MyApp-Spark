package example
import org.apache.spark.SparkContext
import org.apache.log4j.Level
import org.apache.log4j.Logger

object TotalSpend  extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)
    //  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local[*]","wc")
    val input = sc.textFile("/Users/Wolverine/Documents/BigData-Hadoop/Week 9/DataSets/customerorders.csv")
    val mappedInput = input.map(x => (x.split(",")(0),x.split(",")(2).toFloat))
    val totalByCustomer = mappedInput.reduceByKey((x,y) => x+y)
    val sortedTotal = totalByCustomer.sortBy(x => x._2)
    val result = sortedTotal.collect
    result.foreach(println)


    println {
      "harsh"
    }

    //  }
//    scala.io.StdIn.readLine()

}
