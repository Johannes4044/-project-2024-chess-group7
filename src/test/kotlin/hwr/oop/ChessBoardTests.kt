package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.Knight
import hwr.oop.figures.Rook
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

class ChessBoardTests : AnnotationSpec() {
    @Test
    fun `initialize Chessboard does not return false`() {
        val chessBoardExample = ChessBoard.fullBoard()
        assertThat(chessBoardExample.displayBoard()).isNotNull
    }

    @Test
    fun `FEN string is generated correctly`() {
        val chessBoard = ChessBoard.fullBoard()
        val fenString = chessBoard.toFEN()
        assertThat(fenString).isEqualTo("TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst")
    }

    @Test
    fun `FEN string is generated correctly for empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val fenString = chessBoard.toFEN()
        assertThat(fenString).isEqualTo("8/8/8/8/8/8/8/8")
        assertThat(chessBoard.getFigureAt(Position('d', 1))).isNull()
        assertThat(chessBoard.getFigureAt(Position('e', 2))).isNull()
    }

    @Test
    fun `FEN string is generated correctly for custom board`() {
        val chessBoard = ChessBoard(
            mutableMapOf(
                Position('a', 1) to Rook(true),
                Position('b', 2) to Knight(false),
                Position('c', 3) to Bishop(true)
            )
        )
        val fenString = chessBoard.toFEN()
        assertThat(fenString).isEqualTo("8/8/8/8/8/2l5/1S6/t7")
    }

    @Test
    fun `FEN to board conversion works correctly`() {
        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position('d', 1))?.symbol()).isEqualTo("d")
        assertThat(chessBoard.getFigureAt(Position('e', 2))?.symbol()).isEqualTo("b")
    }

    @Test
    fun `FEN to board conversion works correctly for empty board`() {
        val fenString = "8/8/8/8/8/8/8/8"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position('d', 1))).isNull()
        assertThat(chessBoard.getFigureAt(Position('e', 2))).isNull()
    }

    @Test
    fun `fromFEN throws IllegalArgumentException for invalid FEN`() {
        val invalidFEN = "invalidFENString"

        val exception = assertThrows<IllegalArgumentException> {
            ChessBoard.fromFEN(invalidFEN)
        }

        assertThat(exception.message?.contains("Ungültige Figur in FEN") == true)
    }

}