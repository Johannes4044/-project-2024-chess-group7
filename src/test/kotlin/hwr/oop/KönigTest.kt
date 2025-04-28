package hwr.oop

import Bishop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue


class KingTest : AnnotationSpec() {
    fun `King can move if destination is empty`() {
        val King = King(true) // true = White
        val from = Position('e',1)
        val possibleMoves = listOf(
            Position('e', 2),  // nach oben
            Position('d', 1),  // nach links
            Position('f', 1),  // nach rechts
            Position('d', 2),  // oben links (diagonal)
            Position('f', 2),  // oben rechts (diagonal)
        )
        val board = ChessBoard()

        possibleMoves.forEach { board.getFigureAt(it) }

        // Check, if King can move to every given destination
        possibleMoves.forEach { to ->
            assertThat(King.canMove(from, to, board)).isTrue
        }
    }
}