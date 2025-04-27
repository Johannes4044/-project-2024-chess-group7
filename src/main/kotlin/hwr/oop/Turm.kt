package hwr.oop

class Turm(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "t" else "T"
}