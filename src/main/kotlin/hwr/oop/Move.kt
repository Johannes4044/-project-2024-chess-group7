package hwr.oop

import hwr.oop.figures.King
import hwr.oop.figures.Rook


data class Move(
    val from: Position,
    val to: Position,
    val board: ChessBoard,
    val playerBefore: Boolean,
    val totalMovesBefore: Int
) {

    /**
     * Creates a move from one position to another on the given chessboard.
     *
     * @param from The starting position of the move.
     * @param to The target position of the move.
     * @param board The chessboard on which the move is being made.
     */
    override fun toString(): String {
        return "Zug von ${from.column}${from.row} nach ${to.column}${to.row}"
    }

    /**
     * Checks if the move is valid according to the rules of chess.
     *
     * @return True if the move is valid, false otherwise.
     */
    fun isValid(): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        return figure.availableTargets(from, board).contains(to)
    }

    /**
     * Checks if the move is a capture.
     *
     * A move is considered a capture if there is a figure at the target position,
     * and it is of a different color than the moving figure.
     *
     * @return True if the move is a capture, false otherwise.
     */
    fun isCapture(): Boolean {
        val targetFigure = board.getFigureAt(to)
        val movingFigure = board.getFigureAt(from)
        return targetFigure != null && movingFigure != null && targetFigure.color() != movingFigure.color()
    }

    /**
     * Executes the move on the chessboard.
     *
     * @return True if the move was executed successfully, false otherwise.
     * @throws IllegalArgumentException if the move is invalid.
     */

    fun castleKingSide(game: Game): Boolean {
        // Rochade nur für Weiß, von e1 nach g1 (König) und h1 nach f1 (Turm)
        val kingFrom = Position(Column.E, Row.ONE)
        val rookFrom = Position(Column.H, Row.ONE)
        val kingTo = Position(Column.G, Row.ONE)
        val rookTo = Position(Column.F, Row.ONE)

        val king = board.getFigureAt(kingFrom)
        val rook = board.getFigureAt(rookFrom)

        // Prüfe, ob König und Turm vorhanden und noch nicht gezogen haben
        if (king !is King || king.color() != Color.WHITE || king.hasMoved) return false
        if (rook !is Rook || rook.color() != Color.WHITE || rook.hasMoved) return false

        // Prüfe, ob Felder zwischen König und Turm frei sind
        val between = listOf(
            Position(Column.F, Row.ONE),
            Position(Column.G, Row.ONE)
        )
        if (between.any { board.getFigureAt(it) != null }) return false

        // Prüfe, ob König im Schach steht oder durch bedrohte Felder zieht
        val threatened = listOf(
            Position(Column.E, Row.ONE),
            Position(Column.F, Row.ONE),
            Position(Column.G, Row.ONE)
        )
        // Annahme: board.isSpaceFree ist eine Methode, die überprüft, ob das Feld nicht bedroht ist
        if (threatened.any { !board.isSpaceFree(game, it, true) }) return false

        // Rochade ausführen
        board.removePiece(kingFrom)
        board.removePiece(rookFrom)

        // Setze hasMoved manuell auf true, ohne copy
        king.hasMoved = true
        rook.hasMoved = true
        board.placePieces(kingTo, king)
        board.placePieces(rookTo, rook)
        return true
    }
}

