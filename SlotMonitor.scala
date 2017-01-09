package com.knoldus.akka.ParkingLot


import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import com.knoldus.akka.ParkingLot.LevelMonitor.{AllocateSlot, DeallocateSlot}
import com.knoldus.akka.ParkingLot.SlotMonitor.{BookSlot, LeaveSlot}


object SlotMonitor{
      case class BookSlot()
      case class LeaveSlot(slot:Slot)
}

class SlotMonitor(levelMonitorRouterRef: ActorRef) extends Actor with ActorLogging {


  def receive = {
    case BookSlot => {
      log.info("Book Slot Request recieved")
      levelMonitorRouterRef ! AllocateSlot
    }

    case LeaveSlot(slot) => {
      log.info("UnbBook Slot Request recieved")
      levelMonitorRouterRef ! DeallocateSlot(slot)
    }

  }
}


