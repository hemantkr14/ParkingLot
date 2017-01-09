package com.knoldus.akka.ParkingLot

import java.awt.Desktop.Action

import akka.pattern.ask
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing._
import akka.util.Timeout
import com.knoldus.akka.ParkingLot.Attendant.Leave
import com.knoldus.akka.ParkingLot.LevelMonitor.{AllocateSlot, DeallocateSlot}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by hsha25 on 12/29/2016.
  */
object ParkingSystem extends App{

  implicit val timeout = Timeout(5.seconds)
  val parkingSystem = ActorSystem("ParkingSystem")
  val levelMonitorRouter = parkingSystem.actorOf(Props[LevelMonitor].withRouter(RoundRobinPool(3)), "levelMonitorRouter")
  val levelMonitor = parkingSystem.actorOf(Props[LevelMonitor], "levelMonitor")
  val slotMonitor = parkingSystem.actorOf(Props(new SlotMonitor(levelMonitorRouter)), "SlotMonitor")

  val vehicleSlotMap = new scala.collection.mutable.HashMap[String, Slot]()
  //val attendant = parkingSystem.actorOf(Props(new Attendant(slotMonitor)), "Attendant")

  val attendant = parkingSystem.actorOf(Props(new Attendant(slotMonitor)) , "Driver")

  /*parkingSystem.scheduler.scheduleOnce(200.millis) {
    println("Give me Parking")
    val slot = Await.result((levelMonitor ? AllocateSlot).mapTo[Slot], 50.seconds)
    println("Received slot" + slot.toString)

    attendant ! Leave(slot)
  }*/

  1 to 10 map ( x=> {

    parkingSystem.scheduler.scheduleOnce(200.millis) {
      println("Give me Parking")
      val slot = Await.result((levelMonitor ? AllocateSlot).mapTo[Slot], 50.seconds)
      /*(levelMonitor ? AllocateSlot).onSuccess{
        case slot : Slot => println("Received slot" + slot.toString)
      }*/
      //slot.On
      println("Received slot" + slot.toString)
      vehicleSlotMap.put(x.toString(), slot)
    }

  })

  1 to 10 map ( x=> {

    val slot = vehicleSlotMap.get(x.toString()).get
    //slot.On
    println("Vehicle No: " + slot.toString + " is leaving.")
    (slot.level ? DeallocateSlot(slot)).onSuccess{
      case _ => println("Driver left")
    }

  })



  parkingSystem.scheduler.scheduleOnce(200.millis) {
    println("Give me Parking")
    val slot = Await.result((levelMonitor ? AllocateSlot).mapTo[Slot], 50.seconds)
    /*(levelMonitor ? AllocateSlot).onSuccess{
      case slot : Slot => println("Received slot" + slot.toString)
    }*/
    //slot.On
    println("Received slot" + slot.toString)

    (slot.level ? DeallocateSlot(slot)).onSuccess{
      case _ => println("Driver left")
    }


    // attendant ! Leave(slot)
  }

  /*parkingSystem.scheduler.scheduleOnce(200.millis) {
    println("Give me Parking")
    val slot = Await.result((levelMonitor ? DeallocateSlot(new Slot(levelMonitor))).mapTo[Slot], 50.seconds)
    println("Received slot" + slot.toString)

    attendant ! Leave(slot)
  }*/


/*

  parkingSystem.scheduler.scheduleOnce(200.millis) {
    val slot = Await.result((attendant ? Attendant.GiveMeSlot).mapTo[Slot], 5.seconds)
  }

  parkingSystem.scheduler.scheduleOnce(200.millis) {
    val slot = Await.result((attendant ? Attendant.GiveMeSlot).mapTo[Slot], 5.seconds)
  }
*/



}
