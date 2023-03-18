package example

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext


object LogLevelScalaPgm extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)
  val sc = new SparkContext("local[*]","word-count")
  val my_list = List("WARN: Tuesday 4 September 0405",
                      "ERROR: Tuesday 4 September 0408",
                      "ERROR: Tuesday 4 September 0408",
                      "ERROR: Tuesday 4 September 0408",
                      "ERROR: Tuesday 4 September 0405",
                      "ERROR: Tuesday 4 September 0405")

  val original_logs_rdd = sc.parallelize(my_list)
  val new_pair_rdd = original_logs_rdd.map(x => {
    val columns = x.split(":")
    val log_level = columns(0)
    (log_level,1)
  })
  val new_resultant_rdd = new_pair_rdd.reduceByKey((x,y) => x+y)
  new_resultant_rdd.collect().foreach(println)
}
