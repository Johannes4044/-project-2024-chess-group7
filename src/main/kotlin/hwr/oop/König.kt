package hwr.oop

class König(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "k" else "K"
}