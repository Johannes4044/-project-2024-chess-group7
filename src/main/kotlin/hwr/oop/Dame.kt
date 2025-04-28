package hwr.oop

class Dame(isWhite: Boolean) : Figuren(isWhite) {
    override fun symbol() = if (isWhite) "d" else "D"



    override fun canMove(from: Position, to: Position, board: ChessBoard): Boolean {
        val dy = to.Zeile - from.Zeile
        val dx = to.Spalte - from.Spalte

        // Перевірка, чи рух горизонтальний, вертикальний або діагональний
        if (dx != 0 && dy != 0 && kotlin.math.abs(dx) != kotlin.math.abs(dy)) {
            return false
        }

        // Перевірка, чи шлях вільний
        val stepX = if (dx == 0) 0 else dx / kotlin.math.abs(dx)
        val stepY = if (dy == 0) 0 else dy / kotlin.math.abs(dy)

        var current = Position((from.Spalte + stepX).toChar(), from.Zeile + stepY)
        while (current != to) {
            if (board.getFigureAt(current) != null) {
                return false
            }
            current = Position((current.Spalte + stepX).toChar(), current.Zeile + stepY)
        }

        // Перевірка цільової позиції
        val destination = board.getFigureAt(to)
        return destination == null || destination.isWhite != this.isWhite

    }
}

