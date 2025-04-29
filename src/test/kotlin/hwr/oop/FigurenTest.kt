package hwr.oop

import Bishop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FiguresTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val Bauerexample = Pawn(true)
        assertThat(Bauerexample.symbol()).isEqualTo("b")
        assertThat(Bauerexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook gets correctly created`() {
        val Rookexample = Rook(true)
        assertThat(Rookexample.symbol()).isEqualTo("t")
        assertThat(Rookexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook can move vertically`() {
        val chessBoard = ChessBoard()
        val TestRook = Rook(true)
        chessBoard.Board[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('a', 8)


        assertThat(TestRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook can move horizontally`() {
        val chessBoard = ChessBoard()
        val TestRook = Rook(true)
        chessBoard.Board[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('h', 1)

        assertThat(TestRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook cannot move diagonally`() {
        val chessBoard = ChessBoard()
        val TestRook = Rook(true)
        chessBoard.Board[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('h', 8)


        assertThat(TestRook.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Night gets correctly created`() {
        val Springerexample = Knight(true)
        assertThat(Springerexample.symbol()).isEqualTo("s")
        assertThat(Springerexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bishop gets correctly created`() {
        val Laeuferexample = Bishop(true)
        assertThat(Laeuferexample.symbol()).isEqualTo("l")
        assertThat(Laeuferexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Queen gets correctly created`() {
        val Dameexample = Queen(true)
        assertThat(Dameexample.symbol()).isEqualTo("d")
        assertThat(Dameexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `King gets correctly created`() {
        val Königexample = King(true)
        assertThat(Königexample.symbol()).isEqualTo("k")
        assertThat(Königexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Pawn can move one step forward`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        chessBoard.Board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 3)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn can move two steps forward from start position`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        chessBoard.Board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 4)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn can capture diagonally`() {
        val chessBoard = ChessBoard()
        val whitePawn = Pawn(true)
        val blackPawn = Pawn(false)
        chessBoard.Board[Position('a', 4)] = whitePawn
        chessBoard.Board[Position('b', 5)] = blackPawn
        val from = Position('a', 4)
        val to = Position('b', 5)

        assertThat(whitePawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn cannot move backward`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        chessBoard.Board[Position('a', 3)] = pawn
        val from = Position('a', 3)
        val to = Position('a', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Pawn cannot move sideways`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        chessBoard.Board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('b', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }
}