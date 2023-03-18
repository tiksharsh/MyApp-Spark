package example

import scala.io.StdIn.readLine

object RevStringEYAssignment {

  def main(args: Array[String]) {

    val inputVal = readLine()

    // First o/p
    val outputVal = inputVal.reverse
    println(outputVal)

    // Second o/p
    val outputVal2 = inputVal.split(" ").map(x => x.reverse)
    println(outputVal2.mkString(" "))

    // Third o/p
    val outputVal3 = inputVal.split(" ").reverse
    println(outputVal3.mkString(" "))
  }
}

