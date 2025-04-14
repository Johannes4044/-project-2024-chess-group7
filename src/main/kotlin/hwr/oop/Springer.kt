package hwr.oop

class Springer(var positionX: Int, var positionY: Int, val Farbe: String, val istErsterZug: Boolean, val Name: String = "Springer") {

    fun newSpringer(positionX: Int, positionY: Int,Farbe: String, istErsterZug: Boolean, Name: String): Springer {
        return Springer(positionX,positionY ,Farbe, istErsterZug, Name)
    }
}