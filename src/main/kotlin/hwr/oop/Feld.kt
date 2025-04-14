package hwr.oop

import hwr.oop.figuren.SchachFigur

class Feld(
    val positionX: Int,
    val positionY: Int,
    var figur: SchachFigur? = null
) {
    fun isEmpty(): Boolean = figur == null
}