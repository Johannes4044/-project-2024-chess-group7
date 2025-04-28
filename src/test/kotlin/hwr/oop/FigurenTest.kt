package hwr.oop

import Laeufer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FigurenTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val Bauerexample = Bauer(true)
        assertThat(Bauerexample.symbol()).isEqualTo("b")
        assertThat(Bauerexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook gets correctly created`() {
        val Turmexample = Turm(true)
        assertThat(Turmexample.symbol()).isEqualTo("t")
        assertThat(Turmexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Night gets correctly created`() {
        val Springerexample = Springer(true)
        assertThat(Springerexample.symbol()).isEqualTo("s")
        assertThat(Springerexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bishop gets correctly created`() {
        val Laeuferexample = Laeufer(true)
        assertThat(Laeuferexample.symbol()).isEqualTo("l")
        assertThat(Laeuferexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Queen gets correctly created`() {
        val Dameexample = Dame(true)
        assertThat(Dameexample.symbol()).isEqualTo("d")
        assertThat(Dameexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `King gets correctly created`() {
        val Königexample = König(true)
        assertThat(Königexample.symbol()).isEqualTo("k")
        assertThat(Königexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bauer can move `() {
        val TestBauer = Bauer(true)
        val from = Position('a', 2)
        val to = Position('a', 3)
        val chessBoard = ChessBoard()

        assertThat(TestBauer.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Bauer can move two`() {
        val TestBauer = Bauer(true)
        val from = Position('a', 2)
        val to = Position('a', 4)
        val chessBoard = ChessBoard()

        assertThat(TestBauer.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Bauer can capture `() {
        val from = Position('a', 2)
        val to = Position('a', 4)
        val from2 = Position('b', 7)
        val to2 = Position('b', 5)
        val from3 = Position('a', 4)
        val to3 = Position('b', 5)
        val chessBoard = ChessBoard()

        assertThat(chessBoard.move(from, to)).isTrue
        assertThat(chessBoard.move(from2, to2)).isTrue
        assertThat(chessBoard.move(from3, to3)).isTrue
    }
}