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
}