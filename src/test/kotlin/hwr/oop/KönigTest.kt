package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import kotlin.text.set


class KingTest : AnnotationSpec() {

    @Test
    fun `King can move if destination is empty`() {
        val king = King(true) // true = Weiß
        val from = Position('e', 1)
        val possibleMoves = listOf(
            Position('e', 2),  // nach oben
            Position('d', 1),  // nach links
            Position('f', 1),  // nach rechts
            Position('d', 2),  // oben links (diagonal)
            Position('f', 2),  // oben rechts (diagonal)
        )
        val board = ChessBoard.emptyBoard()
        board.board[from] = king

        possibleMoves.forEach { board.getFigureAt(it) }

        possibleMoves.forEach { to ->
            assertThat(king.canMove(from, to, board)).isTrue()
        }
    }

    @Test
    fun `King cannot move more than one square in any direction`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.board[from] = king

        val invalidMoves = listOf(
            Position('e', 6), // zwei Felder nach oben
            Position('g', 4), // zwei Felder nach rechts
            Position('c', 4), // zwei Felder nach links
            Position('e', 2), // zwei Felder nach unten
            Position('g', 6)  // zwei Felder diagonal
        )

        invalidMoves.forEach { to ->
            assertThat(king.canMove(from, to, board)).isFalse()
        }
    }

    @Test
    fun `King cannot move to a square occupied by same color`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.board[from] = king
        val friendly = King(true)
        val to = Position('e', 5)
        board.board[to] = friendly

        assertThat(king.canMove(from, to, board)).isFalse()
    }

    @Test
    fun `King can capture enemy piece`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.board[from] = king
        val enemy = King(false)
        val to = Position('e', 5)
        board.board[to] = enemy

        assertThat(king.availableMoves(from, board)).contains(to)
    }

    @Test
    fun `availableMoves gibt nur gueltige Felder back`() {
        val king = King(true)
        val from = Position('a', 1)
        val board = ChessBoard.emptyBoard()
        board.board[from] = king

        val moves = king.availableMoves(from, board)
        moves.forEach {
            assertThat(it.column in 'a'..'h' && it.row in 1..8).isTrue()
        }
    }
}