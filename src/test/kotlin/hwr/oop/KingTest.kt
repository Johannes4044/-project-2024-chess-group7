package hwr.oop

import hwr.oop.figures.King
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class KingTest : AnnotationSpec() {

    @Test
    fun `King can move if destination is empty`() {
        val king = King(true)
        val from = Position('e', 1)
        val possibleMoves = listOf(
            Position('e', 2),
            Position('d', 1),
            Position('f', 1),
            Position('d', 2),
            Position('f', 2),
        )
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 1), king)

        possibleMoves.forEach { board.getFigureAt(it) }

        possibleMoves.forEach { to ->
            assertThat(king.availableMoves(from, board))
        }
    }

    @Test
    fun `King cannot move more than one square in any direction`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 4), king)

        val invalidMoves = listOf(
            Position('e', 6),
            Position('g', 4),
            Position('c', 4),
            Position('e', 2),
            Position('g', 6)
        )

        invalidMoves.forEach { to ->
            assertThat(king.availableMoves(from, board))
        }
    }

    @Test
    fun `King cannot move to a square occupied by same color`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 4), king)
        val friendly = King(true)
        val to = Position('e', 5)
        board.placePieces(Position('e', 5), friendly)

        assertThat(king.availableMoves(from, board))
    }

    @Test
    fun `King can capture enemy piece`() {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 4), king)
        val enemy = King(false)
        val to = Position('e', 5)
        board.placePieces(Position('e', 5), enemy)

        assertThat(king.availableMoves(from, board)).contains(to)
    }

    @Test
    fun `availableMoves returns only valid squares`() {
        val king = King(true)
        val from = Position('a', 1)
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('a', 1), king)

        val moves = king.availableMoves(from, board)
        moves.forEach {
            assertThat(it.column in 'a'..'h' && it.row in 1..8).isTrue()
        }
    }
}