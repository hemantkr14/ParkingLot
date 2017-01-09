package com.knoldus.akka.ParkingLot
import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.knoldus.akka.ParkingLot.Attendant.GiveMeSlot
import com.knoldus.akka.ParkingLot.Driver.{Leave, Park}
import com.knoldus.akka.ParkingLot.SlotMonitor.{BookSlot, LeaveSlot}


object Attendant {
  case class GiveMeSlot()
  case class Park(slot: Slot)
  case class Leave(slot: Slot)
}
class Attendant(slotMonitorRef : ActorRef) extends Actor with ActorLogging {
  def receive = {
    case GiveMeSlot => {
         // Send the request to Kafka with Vehicle Number
      log.info("Parking request recieved")
      slotMonitorRef.forward(BookSlot)

    }

    case Park(slot) =>{
      log.info("Slot received, Please park")
      LeaveSlot(slot)

      //slotMonitorRef.forward(LeaveSlot(slot))
    }
    case Leave(slot) =>{
      log.info("Leave Parking request recieved")
      slotMonitorRef.forward(LeaveSlot(slot))
    }
  }
}

