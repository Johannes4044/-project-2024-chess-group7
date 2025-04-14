package hwr.oop

class Dame(var positionX: Int, var positionY: Int, val Farbe: String, val istErsterZug: Boolean, val Name: String = "Dame") {

    fun newDame(positionX: Int, positionY: Int,Farbe: String, istErsterZug: Boolean, Name: String): Dame {
        return Dame(positionX,positionY ,Farbe, istErsterZug, Name)
    }
}