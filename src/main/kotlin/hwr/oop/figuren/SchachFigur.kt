package hwr.oop.figuren

import hwr.oop.Position

abstract class SchachFigur(
    val typ: String,
    val farbe: String,
    var position: Position,
    var istErsterZug: Boolean = true
)


