package hwr.oop

class Turm(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "t" else "T"
}