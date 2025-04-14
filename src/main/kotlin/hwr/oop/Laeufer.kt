import hwr.oop.Figuren

class Laeufer(istweiß: Boolean) : Figuren(istweiß) {
    override fun symbol() = if (istweiß) "l" else "L"
}