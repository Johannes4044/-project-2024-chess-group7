package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook
import hwr.oop.figures.King
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MovementTests : AnnotationSpec() {


    @Test
    fun `Bishop available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        chessBoard.placePieces(Position('d', 4), bishop)
        val from = Position('d', 4)

        val moves = bishop.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('e', 5), Position('f', 6), Position('g', 7), Position('h', 8),
            Position('c', 5), Position('b', 6), Position('a', 7),
            Position('e', 3), Position('f', 2), Position('g', 1),
            Position('c', 3), Position('b', 2), Position('a', 1)
        )
    }

    @Test
    fun `Bishop available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position('d', 4), bishop)
        chessBoard.placePieces(Position('e', 5), blockingPawn)
        chessBoard.placePieces(Position('c', 3), opponentPawn)
        val from = Position('d', 4)

        val moves = bishop.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('c', 3),
            Position('e', 3), Position('f', 2), Position('g', 1),
            Position('c', 5), Position('b', 6), Position('a', 7)
        )
    }

    @Test
    fun `knight available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        chessBoard.placePieces(Position('d', 4), knight)
        val from = Position('d', 4)

        val moves = knight.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('b', 3), Position('b', 5), Position('c', 2), Position('c', 6),
            Position('e', 2), Position('e', 6), Position('f', 3), Position('f', 5)
        )
    }

    @Test
    fun `knight available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position('d', 4), knight)
        chessBoard.placePieces(Position('b', 3), blockingPawn)
        chessBoard.placePieces(Position('f', 5), opponentPawn)
        val from = Position('d', 4)

        val moves = knight.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('b', 5), Position('c', 2), Position('c', 6),
            Position('e', 2), Position('e', 6), Position('f', 3), Position('f', 5)
        )
    }

    @Test
    fun `Pawn available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        chessBoard.placePieces(Position('a', 2), pawn)
        val from = Position('a', 2)

        val moves = pawn.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('a', 3), Position('a', 4)
        )
    }

    @Test
    fun `Pawn available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        val opponentPawn2 = Pawn(Color.BLACK)
        chessBoard.placePieces(Position('b', 2), pawn)
        chessBoard.placePieces(Position('b', 3), blockingPawn)
        chessBoard.placePieces(Position('c', 3), opponentPawn)
        chessBoard.placePieces(Position('a', 3), opponentPawn2)
        val from = Position('b', 2)

        val moves = pawn.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('c', 3), Position('a', 3),
        )
    }

    @Test

    fun `Queen available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(Color.WHITE)
        chessBoard.placePieces(Position('d', 4), queen)
        val from = Position('d', 4)

        val moves = queen.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position('d', 1),
            Position('d', 2),
            Position('d', 3),
            Position('d', 5),
            Position('d', 6),
            Position('d', 7),
            Position('d', 8),
            // Horizontal
            Position('a', 4),
            Position('b', 4),
            Position('c', 4),
            Position('e', 4),
            Position('f', 4),
            Position('g', 4),
            Position('h', 4),
            // Diagonal
            Position('a', 1),
            Position('b', 2),
            Position('c', 3),
            Position('e', 5),
            Position('f', 6),
            Position('g', 7),
            Position('h', 8),
            Position('a', 7),
            Position('b', 6),
            Position('c', 5),
            Position('e', 3),
            Position('f', 2),
            Position('g', 1)
        )
    }

    fun `Queen available moves on filled board`() {
        val chessBoard = ChessBoard.emptyBoard()
    }

    @Test
    fun `Rook available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(Color.WHITE)
        chessBoard.placePieces(Position('d', 4), rook)
        val from = Position('d', 4)

        val moves = rook.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position('d', 1),
            Position('d', 2),
            Position('d', 3),
            Position('d', 5),
            Position('d', 6),
            Position('d', 7),
            Position('d', 8),
            // Horizontal
            Position('a', 4),
            Position('b', 4),
            Position('c', 4),
            Position('e', 4),
            Position('f', 4),
            Position('g', 4),
            Position('h', 4)
        )
    }

    // Move Test
    @Test
    fun `isValid returns true for valid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position('e', 2), pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isTrue()
    }

    @Test
    fun `isValid returns false for invalid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 5)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position('e', 2), pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isValid returns false if no figure at from`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isCapture returns true if target is enemy`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position('e', 2), whitePawn)
        board.placePieces(Position('e', 3), blackPawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture returns false if target is same color`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn1 = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        board.placePieces(Position('e', 2), whitePawn1)
        board.placePieces(Position('e', 3), whitePawn2)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `isCapture returns false if target is empty`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(Position('e', 2), whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `execute returns true for valid move`() {
        val board = ChessBoard.fullBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(from, pawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isTrue()
        assertThat(board.getFigureAt(to)).isEqualTo(pawn)
        assertThat(board.getFigureAt(from)).isNull()
    }

    @Test
    fun `execute returns false for invalid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 5)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position('e', 2), pawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isFalse()
        assertThat(board.getFigureAt(from)).isEqualTo(pawn)
        assertThat(board.getFigureAt(to)).isNull()
    }

    // Position Test

    @Test
    fun `Position creates correct object`() {
        val pos = Position('e', 4)
        assertThat(pos.column).isEqualTo('e')
        assertThat(pos.row).isEqualTo(4)
    }

    @Test
    fun `Position equals and hashCode work correctly`() {
        val pos1 = Position('a', 1)
        val pos2 = Position('a', 1)
        val pos3 = Position('b', 2)
        assertThat(pos1).isEqualTo(pos2)
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode())
        assertThat(pos1).isNotEqualTo(pos3)
    }

    @Test
    fun `toString returns correct format`() {
        val pos = Position('d', 5)
        assertThat(pos.toString()).isEqualTo("d5")
    }

    @Test
    fun `toString for Move returns correct format`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val move = Move(from, to, board)
        assertThat(move.toString()).isEqualTo("Zug von e2 nach e3")
    }

    @Test
    fun `isCapture output is correct`() {
        val board = ChessBoard.fullBoard()
        val from = Position('e', 2)
        val to = Position('f', 3)
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position('e', 2), whitePawn)
        board.placePieces(Position('f', 3), blackPawn)


        val move = Move(from, to, board)


        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        try {
            move.execute()
        } finally {
            System.setOut(originalOut)
        }


        val output = outputStream.toString()
        assertThat(output).contains("Piece was captured!")
    }

    @Test
    fun `isCapture returns True`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val from2 = Position('a', 3)
        val to = Position('f', 3)
        val to2 = Position('a', 4)
        val whitePawn = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position('e', 2), whitePawn)
        board.placePieces(Position('f', 3), blackPawn)
        board.placePieces(Position('a', 3), whitePawn2)

        val move = Move(from, to, board)
        val move2 = Move(from2, to2, board)

        assertThat(move.isCapture()).isTrue()
        assertThat(move2.isCapture()).isFalse()
    }
    @Test
    fun `isCapture gibt true zurück wenn Ziel gegnerische Figur ist`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(from, whitePawn)
        board.placePieces(to, blackPawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture gibt false zurück wenn Ziel leer ist`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `execute gibt true zurück für gültigen Zug und entfernt Figur vom Startfeld`() {
        val board = ChessBoard.fullBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isTrue()
        assertThat(board.getFigureAt(from)).isNull()
        assertThat(board.getFigureAt(to)).isEqualTo(whitePawn)
    }

    @Test
    fun `execute gibt false zurück für ungültigen Zug und verändert nichts`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 5)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isFalse()
        assertThat(board.getFigureAt(from)).isEqualTo(whitePawn)
        assertThat(board.getFigureAt(to)).isNull()
    }
}


