package hwr.oop

class Dame(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "d" else "D"
}