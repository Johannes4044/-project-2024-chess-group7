package hwr.oop

class Springer(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "s" else "S"
}