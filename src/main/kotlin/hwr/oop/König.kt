package hwr.oop

class König(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "k" else "K"
}