package hwr.oop

class Springer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "s" else "S"
}