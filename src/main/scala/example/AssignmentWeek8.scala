package example

import scala.io.StdIn._

object AssignmentWeek8 {
  def main(args: Array[String]) {

    // Taking number of customers as input
    val numCustomers:Int = readInt()

    //    Taking second line as Input
    val billAmount: String = readLine()
    val billAmt: Array[Int] = billAmount.split(" ").map(x => x.toInt)

    var count = 0
    for(i <- billAmt){
      val sqrt = Math.sqrt(i)
      if(sqrt.ceil -sqrt == 0){

        count = count + 1

      }
    }
    println(count)
  }
}
