package hwr.oop

class Dame(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "d" else "D"
}