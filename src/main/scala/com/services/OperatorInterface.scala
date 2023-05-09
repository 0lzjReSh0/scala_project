package com.services

object OperatorInterface {
  def main(args: Array[String]): Unit = {
    println("Welcome to the Renewable Energy Plant System. Please select an option:")
    println("1. View energy data")
    println("2. Adjust energy source operation")

    val option = scala.io.StdIn.readInt()

    option match {
      case 1 => DataView.showData(DataStorage.readData())
      case 2 => println("Please enter the ID of the energy source you want to adjust:")
        val id = scala.io.StdIn.readLine()
        println(s"Adjusting operation of energy source: $id")
      // Here you could call a function that adjusts the operation of the energy source
      case _ => println("Invalid option")
    }
  }
}

