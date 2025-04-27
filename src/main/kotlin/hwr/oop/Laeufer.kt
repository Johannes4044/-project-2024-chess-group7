import hwr.oop.Figuren

class Laeufer(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "l" else "L"
}