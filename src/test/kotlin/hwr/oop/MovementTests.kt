package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MovementTests : AnnotationSpec() {


    @Test
    fun `Bishop available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        chessBoard.placePieces(Position.D4, bishop)
        val from = Position.D4

        val moves = bishop.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.E5, Position.F6, Position.G7, Position.H8,
            Position.C5, Position.B6, Position.A7,
            Position.E3, Position.F2, Position.G1,
            Position.C3, Position.B2, Position.A1
        )
    }

    @Test
    fun `Bishop available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position.D4, bishop)
        chessBoard.placePieces(Position.E5, blockingPawn)
        chessBoard.placePieces(Position.C3, opponentPawn)
        val from = Position.D4

        val moves = bishop.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.C3,
            Position.E3, Position.F2, Position.G1,
            Position.C5, Position.B6, Position.A7
        )
    }

    @Test
    fun `knight available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        chessBoard.placePieces(Position.D4, knight)
        val from = Position.D4

        val moves = knight.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.B3, Position.B5, Position.C2, Position.C6,
            Position.E2, Position.E6, Position.F3, Position.F5
        )
    }

    @Test
    fun `knight available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position.D4, knight)
        chessBoard.placePieces(Position.B3, blockingPawn)
        chessBoard.placePieces(Position.F5, opponentPawn)
        val from = Position.D4

        val moves = knight.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.B5, Position.C2, Position.C6,
            Position.E2, Position.E6, Position.F3, Position.F5
        )
    }

    @Test
    fun `Pawn available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        chessBoard.placePieces(Position.A2, pawn)
        val from = Position.A2

        val moves = pawn.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.A3, Position.A4
        )
    }

    @Test
    fun `Pawn available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        val opponentPawn2 = Pawn(Color.BLACK)
        chessBoard.placePieces(Position.B2, pawn)
        chessBoard.placePieces(Position.B3, blockingPawn)
        chessBoard.placePieces(Position.C3, opponentPawn)
        chessBoard.placePieces(Position.A3, opponentPawn2)
        val from = Position.B2

        val moves = pawn.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position.C3, Position.A3,
        )
    }

    @Test

    fun `Queen available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(Color.WHITE)
        chessBoard.placePieces(Position.D4, queen)
        val from = Position.D4

        val moves = queen.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position.D1,
            Position.D2,
            Position.D3,
            Position.D5,
            Position.D6,
            Position.D7,
            Position.D8,
            // Horizontal
            Position.A4,
            Position.B4,
            Position.C4,
            Position.E4,
            Position.F4,
            Position.G4,
            Position.H4,
            // Diagonal
            Position.A1,
            Position.B2,
            Position.C3,
            Position.E5,
            Position.F6,
            Position.G7,
            Position.H8,
            Position.A7,
            Position.B6,
            Position.C5,
            Position.E3,
            Position.F2,
            Position.G1
        )
    }

    fun `Queen available moves on filled board`() {
        val chessBoard = ChessBoard.emptyBoard()
    }

    @Test
    fun `Rook available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(Color.WHITE)
        chessBoard.placePieces(Position.D4, rook)
        val from = Position.D4

        val moves = rook.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position.D1,
            Position.D2,
            Position.D3,
            Position.D5,
            Position.D6,
            Position.D7,
            Position.D8,
            // Horizontal
            Position.A4,
            Position.B4,
            Position.C4,
            Position.E4,
            Position.F4,
            Position.G4,
            Position.H4
        )
    }

    // Move Test
    @Test
    fun `isValid returns true for valid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position.E2, pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isTrue()
    }

    @Test
    fun `isValid returns false for invalid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E5
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position.E2, pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isValid returns false if no figure at from`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isCapture returns true if target is enemy`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position.E2, whitePawn)
        board.placePieces(Position.E3, blackPawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture returns false if target is same color`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn1 = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        board.placePieces(Position.E2, whitePawn1)
        board.placePieces(Position.E3, whitePawn2)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `isCapture returns false if target is empty`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(Position.E2, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `execute returns true for valid move`() {
        val board = ChessBoard.fullBoard()
        val from = Position.E2
        val to = Position.E3
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
        val from = Position.E2
        val to = Position.E5
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position.E2, pawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isFalse()
        assertThat(board.getFigureAt(from)).isEqualTo(pawn)
        assertThat(board.getFigureAt(to)).isNull()
    }

    // Position Test

    @Test
    fun `Position creates correct object`() {
        val pos = Position.E4
        assertThat(pos.column).isEqualTo('e')
        assertThat(pos.row).isEqualTo(4)
    }

    @Test
    fun `Position equals and hashCode work correctly`() {
        val pos1 = Position.A1
        val pos2 = Position.A1
        val pos3 = Position.B2
        assertThat(pos1).isEqualTo(pos2)
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode())
        assertThat(pos1).isNotEqualTo(pos3)
    }

    @Test
    fun `toString returns correct format`() {
        val pos = Position.D5
        assertThat(pos.toString()).isEqualTo("d5")
    }

    @Test
    fun `toString for Move returns correct format`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val move = Move(from, to, board)
        assertThat(move.toString()).isEqualTo("Zug von e2 nach e3")
    }

    @Test
    fun `isCapture returns True`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val from2 = Position.A3
        val to = Position.F3
        val to2 = Position.A4
        val whitePawn = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position.E2, whitePawn)
        board.placePieces(Position.F3, blackPawn)
        board.placePieces(Position.A3, whitePawn2)

        val move = Move(from, to, board)
        val move2 = Move(from2, to2, board)

        assertThat(move.isCapture()).isTrue()
        assertThat(move2.isCapture()).isFalse()
    }
    @Test
    fun `isCapture returns true if target is opponent figure`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(from, whitePawn)
        board.placePieces(to, blackPawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture returns false if destination is empty`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `execute returns true for valid move and removes figure from start field`() {
        val board = ChessBoard.fullBoard()
        val from = Position.E2
        val to = Position.E3
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isTrue()
        assertThat(board.getFigureAt(from)).isNull()
        assertThat(board.getFigureAt(to)).isEqualTo(whitePawn)
    }

    @Test
    fun `execute returns false for invalid move and does not change anything`() {
        val board = ChessBoard.emptyBoard()
        val from = Position.E2
        val to = Position.E5
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.execute()).isFalse()
        assertThat(board.getFigureAt(from)).isEqualTo(whitePawn)
        assertThat(board.getFigureAt(to)).isNull()
    }
}


