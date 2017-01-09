package com.knoldus.akka.ParkingLot

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.knoldus.akka.ParkingLot.Driver.{GiveMeSlot, Park , Leave}

object Driver {
  case class GiveMeSlot()
  case class Park(slot: Slot)
  case class Leave(slot: Slot)
}

class Driver extends Actor  {


  def receive = {
    case GiveMeSlot => {


    }

    case Park(slot) =>
    case Leave(slot) =>
  }

  /*implicit val timeout = Timeout(5.seconds)
  val system = ActorSystem("Attendant")
  val attendant = system.actorOf(Props[Attendant], "Attendant")
  //(new SlotMonitor).doSome(1);

  // Grab the controls
  var parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
  println(parkingSlot)
  system.scheduler.scheduleOnce(200.millis) {
    parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
    println("1=>"+parkingSlot)
  }
  system.scheduler.scheduleOnce(1.seconds) {
    parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
    println("2=>"+parkingSlot)
  }

  system.scheduler.scheduleOnce(3.seconds) {
    parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
    println("3=>"+parkingSlot)
  }

  system.scheduler.scheduleOnce(4.seconds) {
    parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
    println("4=>"+parkingSlot)
  }

  system.scheduler.scheduleOnce(4.seconds) {
    attendant ! Attendant.ReturnParkingSlot(8)
  }

  system.scheduler.scheduleOnce(4.seconds) {
    parkingSlot = Await.result((attendant ? Attendant.AssignMeParkingSlot).mapTo[Int], 5.seconds)
    println("5=>"+parkingSlot)
  }

  system.scheduler.scheduleOnce(4.seconds) {
    attendant ! Attendant.ReturnParkingSlot(9)
  }

  // Shut down
  system.scheduler.scheduleOnce(5.seconds) {
    system.terminate()
  }*/

}
