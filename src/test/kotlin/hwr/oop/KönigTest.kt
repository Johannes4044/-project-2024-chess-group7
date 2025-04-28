package hwr.oop

import Laeufer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue


class KönigTest : AnnotationSpec() {
    fun `King can move if destination is empty`() {
        val bauer = König(true) // true = White
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
            assertThat(König.canMove(from, to, board)).isTrue
        }
    }
}