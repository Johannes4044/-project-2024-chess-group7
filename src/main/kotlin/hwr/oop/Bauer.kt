package hwr.oop


class Bauer(var positionX: Int,var positionY: Int ,val Farbe: String, val istErsterZug: Boolean, val Name: String = "Bauer") {
    // Function to check if the pawn can move

    fun newBauer(positionX: Int, positionY: Int,Farbe: String, istErsterZug: Boolean, Name: String): Bauer {
        return Bauer(positionX,positionY ,Farbe, istErsterZug, Name)
    }
}