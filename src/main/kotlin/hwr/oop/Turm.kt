package hwr.oop

class Turm(var positionX: Int, var positionY: Int, val Farbe: String, val istErsterZug: Boolean, val Name: String = "Turm") {

    fun newTurm(positionX: Int, positionY: Int,Farbe: String, istErsterZug: Boolean, Name: String): Turm {
        return Turm(positionX,positionY ,Farbe, istErsterZug, Name)
    }
}