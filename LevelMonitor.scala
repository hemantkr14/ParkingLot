package com.knoldus.akka.ParkingLot

import akka.actor.{Actor, ActorLogging, ActorRef, Status}
import com.knoldus.akka.ParkingLot.Attendant.Park
import com.knoldus.akka.ParkingLot.LevelMonitor.{AllocateSlot, DeallocateSlot}

/**
  * Created by hsha25 on 12/29/2016.
  */

object LevelMonitor{
  case class AllocateSlot()
  case class DeallocateSlot(slot: Slot)
}

case class Slot(level : ActorRef)

class LevelMonitor extends Actor with ActorLogging{

  val MAX_SLOTS = 5
  val parkingSlotsList = new scala.collection.mutable.ListBuffer[Slot]

  override def receive: Receive = {
    case AllocateSlot =>{
      if(parkingSlotsList.size == MAX_SLOTS) {
        log info ("No Parking slots.")
         sender ! None
      }
      else {
        log info (s"Available Parking slots : ${MAX_SLOTS - parkingSlotsList.size}")
        val slot = new Slot(self)
        parkingSlotsList.+=:(slot)
        log info (s"Booked a Slot now Available Parking slots : ${MAX_SLOTS - parkingSlotsList.size}")
        //Option(slot)
        sender() ! slot
      }
    }

    case DeallocateSlot(slot) =>{
      log info (s"Available Parking slots : ${MAX_SLOTS - parkingSlotsList.size}")
      //val slot = new Slot(self)
      log info (s"Deallocating Parking slots : ${slot.toString}")
      parkingSlotsList.-=(slot)
      // parkingSlotsList.remove(1)
      log info (s"Unbooked a Slot now Available Parking slots : ${MAX_SLOTS - parkingSlotsList.size}")
      sender() ! Status.Success
      //Option(slot)
      //sender() ! slot

    }

  }
}
