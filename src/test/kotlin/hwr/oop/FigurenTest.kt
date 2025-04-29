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
        ChessBoard.emptyBoard()[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('a', 8)


        assertThat(TestRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook can move horizontally`() {
        val chessBoard = ChessBoard()
        val TestRook = Rook(true)
        ChessBoard.emptyBoard()[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('h', 1)

        assertThat(TestRook.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Rook cannot move diagonally`() {
        val chessBoard = ChessBoard()
        val TestRook = Rook(true)
        ChessBoard.emptyBoard()[Position('a', 1)] = TestRook
        val from = Position('a', 1)
        val to = Position('h', 8)


        assertThat(TestRook.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Knight gets correctly created`() {
        val Springerexample = Knight(true)
        assertThat(Springerexample.symbol()).isEqualTo("s")
        assertThat(Springerexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Knight can only move one straight and one diagonal`(){
        val knightExample = Knight(true)
        val Board = ChessBoard.emptyBoard()
        val from = Position('b', 1)
        val to = Position('c', 3)

        assertThat(knightExample.canMove(from, to, ChessBoard())).isTrue
    }

    @Test
    fun `Knight cant move irregular`() {
        val KnightExample = Knight(true)
        val Board = ChessBoard.emptyBoard()
        val from = Position('b', 1)
        val irregMoves = listOf(
            Position('b', 3), //vertikal
            Position('d', 1), //horizontal
            Position('c', 2), //diagonal
        )

        irregMoves.forEach {to ->
            assertThat (KnightExample.canMove(from, to, ChessBoard())).isFalse
        }
    }


    @Test
    fun `Bishop gets correctly created`() {
        val Laeuferexample = Bishop(true)
        assertThat(Laeuferexample.symbol()).isEqualTo("l")
        assertThat(Laeuferexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bishop moves diagonal`(){
        val bishopExample = Bishop(true)
        val Board = ChessBoard.emptyBoard()
        val from  = Position('c', 1)
        val to = Position('e', 3)
        assertThat(bishopExample.canMove(from, to, ChessBoard())).isTrue
    }

    @Test
    fun `Bishop cant move irregular`(){
        val bishoptExample = Bishop(true)
        val Board = ChessBoard.emptyBoard()
        val from = Position('c', 1)
        val irregMoves = listOf(
                Position ('c' , 2),
                Position ('f', 1),
        )

        irregMoves.forEach {to ->
            assertThat(bishoptExample.canMove(from, to, ChessBoard())).isFalse
        }
    }


    @Test
    fun `Queen gets correctly created`() {
        val Dameexample = Queen(true)
        assertThat(Dameexample.symbol()).isEqualTo("d")
        assertThat(Dameexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Queen moves regular`(){
        val queenExample = Queen(true)
        val Board = ChessBoard.emptyBoard()
        val from = Position('d', 1)
        val possibleMoves = listOf(
            Position ('d', 5),  //vertical
            Position ('H', 1),  //horizontal
            Position ('f', 2)   //diagonal
        )
        possibleMoves.forEach {to ->
            assertThat(queenExample.canMove(from, to, ChessBoard())).isTrue
        }
    }

    @Test
    fun `Queen cant move irregular`(){
        val queenExample = Queen(true)
        val Board = ChessBoard.emptyBoard()
        val from = Position('d', 1)
        val to = Position('f', 3)
        assertThat(queenExample.canMove(from, to, ChessBoard())).isFalse
    }

    @Test
    fun `King gets correctly created`() {
        val Königexample = King(true)
        assertThat(Königexample.symbol()).isEqualTo("k")
        assertThat(Königexample.isWhite).isEqualTo(true)
    }

    @Test
    fun `King can move if destination is empty`() {
        val King = King(true) // true = White
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
            assertThat(King.canMove(from, to, board)).isTrue
        }
    }

    @Test
    fun `Pawn can move one step forward`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        ChessBoard.emptyBoard()[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 3)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn can move two steps forward from start position`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        ChessBoard.emptyBoard()[Position('a', 2)] = pawn
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
        ChessBoard.emptyBoard()[Position('a', 4)] = whitePawn
        ChessBoard.emptyBoard()[Position('b', 5)] = blackPawn
        val from = Position('a', 4)
        val to = Position('b', 5)

        assertThat(whitePawn.canMove(from, to, chessBoard)).isTrue
        assertThat(chessBoard.move(from, to)).isTrue
    }

    @Test
    fun `Pawn cannot move backward`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        ChessBoard.emptyBoard()[Position('a', 3)] = pawn
        val from = Position('a', 3)
        val to = Position('a', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }

    @Test
    fun `Pawn cannot move sideways`() {
        val chessBoard = ChessBoard()
        val pawn = Pawn(true)
        ChessBoard.emptyBoard()[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('b', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse
    }
}