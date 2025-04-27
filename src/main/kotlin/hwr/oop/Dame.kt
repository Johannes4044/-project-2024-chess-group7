package hwr.oop

class Dame(istweiß: Boolean) : Figuren(istweiß) {

    // Gibt das Symbol der Dame zurück: "d" für weiß, "D" für schwarz.
    override fun symbol() = if (istweiß) "d" else "D"

    // Überprüft, ob die Dame von einer Position zu einer anderen ziehen kann.
    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val dy = to.Zeile - from.Zeile // Differenz der Zeilen
        val dx = to.Spalte - from.Spalte // Differenz der Spalten

        // Überprüfung, ob die Bewegung horizontal, vertikal oder diagonal ist
        if (dx != 0 && dy != 0 && kotlin.math.abs(dx) != kotlin.math.abs(dy)) {
            return false
        }

        // Überprüfung, ob der Weg frei ist
        val stepX = if (dx == 0) 0 else dx / kotlin.math.abs(dx) // Schrittweite in X-Richtung
        val stepY = if (dy == 0) 0 else dy / kotlin.math.abs(dy) // Schrittweite in Y-Richtung

        var current = Position((from.Spalte + stepX).toChar(), from.Zeile + stepY)
        while (current != to) {
            // Wenn eine Figur auf dem Weg steht, ist die Bewegung ungültig
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position((current.Spalte + stepX).toChar(), current.Zeile + stepY)
        }

        // Überprüfung der Zielposition
        val destination = board.getFigureAt(to)
        return destination == null || destination.istweiß != this.istweiß
    }
}