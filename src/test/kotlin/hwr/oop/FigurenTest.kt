package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FiguresTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val pawnExample = Pawn(true)
        assertThat(pawnExample.symbol()).isEqualTo("b")
        assertThat(pawnExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook gets correctly created`() {
        val rookExample = Rook(true)
        assertThat(rookExample.symbol()).isEqualTo("t")
        assertThat(rookExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook can move vertically`() {
        val chessBoard = ChessBoard.emptyBoard()
        val testRook = Rook(true)
        chessBoard.board[Position('a',1)] = testRook
        val from = Position('a', 1)
        val to = Position('a', 8)


        assertThat(testRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook can move horizontally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val testRook = Rook(true)
        chessBoard.board[Position('a', 1)] = testRook
        val from = Position('a', 1)
        val to = Position('h', 1)

        assertThat(testRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook cannot move diagonally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val testRook = Rook(true)
        chessBoard.board[Position('a', 1)] = testRook
        val from = Position('a', 1)
        val to = Position('h', 8)


        assertThat(testRook.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Knight gets correctly created`() {
        val knightExample = Knight(true)
        assertThat(knightExample.symbol()).isEqualTo("s")
        assertThat(knightExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bishop gets correctly created`() {
        val bishopExample = Bishop(true)
        assertThat(bishopExample.symbol()).isEqualTo("l")
        assertThat(bishopExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Queen gets correctly created`() {
        val queenExample = Queen(true)
        assertThat(queenExample.symbol()).isEqualTo("d")
        assertThat(queenExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `King gets correctly created`() {
        val kingExample = King(true)
        assertThat(kingExample.symbol()).isEqualTo("k")
        assertThat(kingExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Pawn can move one step forward`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 3)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn can move two steps forward from start position`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 4)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn can capture diagonally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val whitePawn = Pawn(true)
        val blackPawn = Pawn(false)
        chessBoard.board[Position('a', 4)] = whitePawn
        chessBoard.board[Position('b', 5)] = blackPawn
        val from = Position('a', 4)
        val to = Position('b', 5)

        assertThat(whitePawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn cannot move backward`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 3)] = pawn
        val from = Position('a', 3)
        val to = Position('a', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Pawn cannot move sideways`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('b', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }
}