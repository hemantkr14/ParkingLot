class Car(val make: String, var reserved: Boolean) {
   def reserve(r: Boolean): Unit = {
     reserved = r
   }
}

class Lotus(val color: String, var reserve: Boolean) extends Car("MyMake", false) {

}