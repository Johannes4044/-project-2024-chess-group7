package hwr.oop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import Bishop

class BishopTest : AnnotationSpec() {
    @Test
    fun `Bishop can move if destination is empty`() {
        val bishop = Bishop(true) //
        val from = Position('c', 1)
        val possibleMoves = listOf(
            Position('d', 2),  // oben rechts (diagonal)
            Position('e', 3),  // weiter oben rechts
            Position('b', 2),  // oben links (diagonal)
            Position('a', 3)   // weiter oben links
        )
        val board = ChessBoard()

        possibleMoves.forEach { board.getFigureAt(it) }

        // Check if Bishop can move to every given destination
        possibleMoves.forEach { to ->
            assertThat(bishop.canMove(from, to, board)).isTrue
        }
    }

    @Test
    fun `Bishop can only move diagonal` (){
        val bishop = Bishop(true)
        val from = Position('c', 1)
        val possibleMoves = listOf(
            Position('c', 2),
            Position('c', 5),
        )
        val board = ChessBoard()
        possibleMoves.forEach { board.getFigureAt(it) }
        possibleMoves.forEach { to ->
            assertThat(bishop.canMove(from, to, board)).isFalse
        }
    }

}
