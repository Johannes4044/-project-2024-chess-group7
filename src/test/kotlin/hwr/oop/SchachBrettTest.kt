package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

// TODO Delete this placeholder test.
class SchachBrettTest : AnnotationSpec() {
  @Test
  fun `initialize Chessboard does not return false`() {
    val chessboardexample = ChessBoard()
    assertThat(chessboardexample.initializeBoard()).isNotNull
    assertThat(chessboardexample.displayBoard()).isNotNull
  }


}