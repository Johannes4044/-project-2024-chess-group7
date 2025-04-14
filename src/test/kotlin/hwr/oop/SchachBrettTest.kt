package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class SchachBrettTest : AnnotationSpec() {
  @Test
  fun `initialize Chessboard does not return false`() {
    val chessboardexample = ChessBoard()
    assertThat(chessboardexample.displayBoard()).isNotNull
  }


}