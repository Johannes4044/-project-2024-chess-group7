package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class SchachBrettTest : AnnotationSpec() {
  @Test
  fun `initialize Chessboard does not return false`() {
    val chessboardexample = ChessBoard.fullBoard()
    assertThat(chessboardexample.displayBoard()).isNotNull
  }
  @Test
  fun `CLI command chess new_game initializes the game`() {
    val input = "chess new_game 12345"
    val chessBoard = ChessBoard.fullBoard()

    // Simulierte Eingabe
    val id = input.removePrefix("chess new_game").trim()
    assertThat(id).isEqualTo("12345")

    // Überprüfung der Initialisierung
    chessBoard.displayBoard()
    assertThat(chessBoard.getFigureAt(Position('d', 1))?.symbol()).isEqualTo("d")
    assertThat(chessBoard.getFigureAt(Position('e', 2))?.symbol()).isEqualTo("b")
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
    }

    @Test
    fun `FEN to board conversion works correctly`() {
        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position('d', 1))?.symbol()).isEqualTo("d")
        assertThat(chessBoard.getFigureAt(Position('e', 2))?.symbol()).isEqualTo("b")
    }
}