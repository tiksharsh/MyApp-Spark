package example
import scala.io.StdIn.readLine


object StrictlyLessarAssignmentWeek8 {

  def main(args: Array[String]) {

    val inputline1: String = readLine()
    val inputline1Arr: Array[Int] = inputline1.split(" ").map(x => x.toInt)

    val inputline2: String = readLine()
    val inputline2Arr: Array[Int] = inputline2.split(" ").map(x => x.toInt)

    var count = 0
    val numK = inputline1Arr(1)
    for(i <- inputline2Arr) {
      if(i < numK) {
        count = count +1
      }
    }
    println(count)
  }
}

