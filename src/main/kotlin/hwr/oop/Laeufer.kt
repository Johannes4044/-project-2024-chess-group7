package hwr.oop

class Laeufer(var positionX: Int, var positionY: Int, val Farbe: String, val istErsterZug: Boolean, val Name: String = "Laeufer") {
    // Function to check if the pawn can move

    fun newLaeufer(positionX: Int, positionY: Int,Farbe: String, istErsterZug: Boolean, Name: String): Laeufer {
        return Laeufer(positionX,positionY ,Farbe, istErsterZug, Name)
    }
}