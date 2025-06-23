package hwr.oop

import hwr.oop.figures.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MovementTests : AnnotationSpec() {

    @Test
    fun `Bishop available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), bishop)
        val from = Position(Column.D, Row.FOUR)

        val moves = bishop.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.E, Row.FIVE),
            Position(Column.F, Row.SIX),
            Position(Column.G, Row.SEVEN),
            Position(Column.H, Row.EIGHT),
            Position(Column.C, Row.FIVE),
            Position(Column.B, Row.SIX),
            Position(Column.A, Row.SEVEN),
            Position(Column.E, Row.THREE),
            Position(Column.F, Row.TWO),
            Position(Column.G, Row.ONE),
            Position(Column.C, Row.THREE),
            Position(Column.B, Row.TWO),
            Position(Column.A, Row.ONE)
        )
    }

    @Test
    fun `Bishop available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), bishop)
        chessBoard.placePieces(Position(Column.E, Row.FIVE), blockingPawn)
        chessBoard.placePieces(Position(Column.C, Row.THREE), opponentPawn)
        val from = Position(Column.D, Row.FOUR)

        val moves = bishop.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.C, Row.THREE),
            Position(Column.E, Row.THREE), Position(Column.F, Row.TWO), Position(Column.G, Row.ONE),
            Position(Column.C, Row.FIVE), Position(Column.B, Row.SIX), Position(Column.A, Row.SEVEN)
        )
    }

    @Test
    fun `knight available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), knight)
        val from = Position(Column.D, Row.FOUR)

        val moves = knight.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.B, Row.THREE),
            Position(Column.B, Row.FIVE),
            Position(Column.C, Row.TWO),
            Position(Column.C, Row.SIX),
            Position(Column.E, Row.TWO),
            Position(Column.E, Row.SIX),
            Position(Column.F, Row.THREE),
            Position(Column.F, Row.FIVE)
        )
    }

    @Test
    fun `knight available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), knight)
        chessBoard.placePieces(Position(Column.B, Row.THREE), blockingPawn)
        chessBoard.placePieces(Position(Column.F, Row.FIVE), opponentPawn)
        val from = Position(Column.D, Row.FOUR)

        val moves = knight.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.B, Row.FIVE),
            Position(Column.C, Row.TWO),
            Position(Column.C, Row.SIX),
            Position(Column.E, Row.TWO),
            Position(Column.E, Row.SIX),
            Position(Column.F, Row.THREE),
            Position(Column.F, Row.FIVE)
        )
    }

    @Test
    fun `Pawn available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        chessBoard.placePieces(Position(Column.A, Row.TWO), pawn)
        val from = Position(Column.A, Row.TWO)

        val moves = pawn.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.A, Row.THREE), Position(Column.A, Row.FOUR)
        )
    }

    @Test
    fun `Pawn available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        val blockingPawn = Pawn(Color.WHITE)
        val opponentPawn = Pawn(Color.BLACK)
        val opponentPawn2 = Pawn(Color.BLACK)
        chessBoard.placePieces(Position(Column.B, Row.TWO), pawn)
        chessBoard.placePieces(Position(Column.B, Row.THREE), blockingPawn)
        chessBoard.placePieces(Position(Column.C, Row.THREE), opponentPawn)
        chessBoard.placePieces(Position(Column.A, Row.THREE), opponentPawn2)
        val from = Position(Column.B, Row.TWO)

        val moves = pawn.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position(Column.C, Row.THREE), Position(Column.A, Row.THREE),
        )
    }

    @Test
    fun `Queen available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(Color.WHITE)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), queen)
        val from = Position(Column.D, Row.FOUR)

        val moves = queen.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position(Column.D, Row.ONE),
            Position(Column.D, Row.TWO),
            Position(Column.D, Row.THREE),
            Position(Column.D, Row.FIVE),
            Position(Column.D, Row.SIX),
            Position(Column.D, Row.SEVEN),
            Position(Column.D, Row.EIGHT),
            // Horizontal
            Position(Column.A, Row.FOUR),
            Position(Column.B, Row.FOUR),
            Position(Column.C, Row.FOUR),
            Position(Column.E, Row.FOUR),
            Position(Column.F, Row.FOUR),
            Position(Column.G, Row.FOUR),
            Position(Column.H, Row.FOUR),
            // Diagonal
            Position(Column.A, Row.ONE),
            Position(Column.B, Row.TWO),
            Position(Column.C, Row.THREE),
            Position(Column.E, Row.FIVE),
            Position(Column.F, Row.SIX),
            Position(Column.G, Row.SEVEN),
            Position(Column.H, Row.EIGHT),
            Position(Column.A, Row.SEVEN),
            Position(Column.B, Row.SIX),
            Position(Column.C, Row.FIVE),
            Position(Column.E, Row.THREE),
            Position(Column.F, Row.TWO),
            Position(Column.G, Row.ONE)
        )
    }

    @Test
    fun `Rook available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(Color.WHITE)
        chessBoard.placePieces(Position(Column.D, Row.FOUR), rook)
        val from = Position(Column.D, Row.FOUR)

        val moves = rook.availableTargets(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position(Column.D, Row.ONE),
            Position(Column.D, Row.TWO),
            Position(Column.D, Row.THREE),
            Position(Column.D, Row.FIVE),
            Position(Column.D, Row.SIX),
            Position(Column.D, Row.SEVEN),
            Position(Column.D, Row.EIGHT),
            // Horizontal
            Position(Column.A, Row.FOUR),
            Position(Column.B, Row.FOUR),
            Position(Column.C, Row.FOUR),
            Position(Column.E, Row.FOUR),
            Position(Column.F, Row.FOUR),
            Position(Column.G, Row.FOUR),
            Position(Column.H, Row.FOUR)
        )
    }

    // Move Test
    @Test
    fun `isValid returns true for valid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position(Column.E, Row.TWO), pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isTrue()
    }

    @Test
    fun `isValid returns false for invalid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FIVE)
        val pawn = Pawn(Color.WHITE)
        board.placePieces(Position(Column.E, Row.TWO), pawn)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isValid returns false if no figure at from`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val move = Move(from, to, board)
        assertThat(move.isValid()).isFalse()
    }

    @Test
    fun `isCapture returns true if target is enemy`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val whitePawn = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position(Column.E, Row.TWO), whitePawn)
        board.placePieces(Position(Column.E, Row.THREE), blackPawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture returns false if target is same color`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val whitePawn1 = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        board.placePieces(Position(Column.E, Row.TWO), whitePawn1)
        board.placePieces(Position(Column.E, Row.THREE), whitePawn2)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `isCapture returns false if target is empty`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(Position(Column.E, Row.TWO), whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    // Position Test

    @Test
    fun `from returns null for invalid column`() {
        val position = Position.from('Z', 1)
        assertThat(position).isNull()
    }

    @Test
    fun `from returns null for invalid row`() {
        val position = Position.from('A', 99)
        assertThat(position).isNull()
    }

    @Test
    fun `Position creates correct object`() {
        val pos = Position(Column.E, Row.FOUR)
        assertThat(pos.column).isEqualTo(Column.E)
        assertThat(pos.row).isEqualTo(Row.FOUR)
    }

    @Test
    fun `Position equals and hashCode work correctly`() {
        val pos1 = Position(Column.A, Row.ONE)
        val pos2 = Position(Column.A, Row.ONE)
        assertThat(pos1).isEqualTo(pos2)
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode())
    }

    @Test
    fun `toString returns correct format`() {
        val pos = Position(Column.D, Row.FIVE)
        assertThat(pos.toString()).isEqualTo("DFIVE")
    }

    @Test
    fun `toString for Move returns correct format`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val move = Move(from, to, board)
        assertThat(move.toString()).isEqualTo("Zug von ETWO nach ETHREE")
    }

    @Test
    fun `isCapture returns True`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val from2 = Position(Column.A, Row.THREE)
        val to = Position(Column.F, Row.THREE)
        val to2 = Position(Column.A, Row.FOUR)
        val whitePawn = Pawn(Color.WHITE)
        val whitePawn2 = Pawn(Color.WHITE)
        val blackPawn = Pawn(Color.BLACK)
        board.placePieces(Position(Column.E, Row.TWO), whitePawn)
        board.placePieces(Position(Column.F, Row.THREE), blackPawn)
        board.placePieces(Position(Column.A, Row.THREE), whitePawn2)

        val move = Move(from, to, board)
        val move2 = Move(from2, to2, board)

        assertThat(move.isCapture()).isTrue()
        assertThat(move2.isCapture()).isFalse()
    }

    @Test
    fun `isCapture returns true if target is opponent figure`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
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
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.THREE)
        val whitePawn = Pawn(Color.WHITE)
        board.placePieces(from, whitePawn)
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
     fun `makeMove throws exception for invalid move and does not change board`() {
         val board = ChessBoard.emptyBoard()
         val from = Position(Column.E, Row.TWO)
         val to = Position(Column.E, Row.FIVE)
         val whitePawn = Pawn(Color.WHITE)
         board.placePieces(from, whitePawn)
         val game = Game()
         game.board = board
         val exception = shouldThrow<IllegalStateException> {
             game.makeMove(from, to)
         }
         assertThat(exception.message).isEqualTo("Invalid move from ETWO to EFIVE")
         assertThat(board.getFigureAt(from)).isEqualTo(whitePawn)
         assertThat(board.getFigureAt(to)).isNull()
     }

    @Test
    fun `from returns null for invalid column Z`() {
        val position = Position.from('Z', 1)
        assertThat(position).isNull()
    }

    @Test
    fun `from returns null for invalid row A`() {
        val position = Position.from('A', 99)
        assertThat(position).isNull()
    }

    @Test
    fun `removed piece position returns null`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(Color.WHITE)
        val position = Position(Column.E, Row.FOUR)
        chessBoard.placePieces(position, pawn)

        chessBoard.removePiece(position)

        assertThat(chessBoard.getFigureAt(position)).isNull()
    }


    @Test
    fun `castleKingSideWhite executes correctly`() {
        val chessBoard = ChessBoard.emptyBoard()
        val game = Game()


        // Place the king and rook
        chessBoard.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        chessBoard.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        game.board = chessBoard
        // Perform the castling move
        val result = game.whiteKingsideCastling(game)

        // Check if the castling was successful

        assertThat(result).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.F, Row.ONE))).isInstanceOf(Rook::class.java)
        assertThat(chessBoard.getFigureAt(Position(Column.G, Row.ONE))).isInstanceOf(King::class.java)
    }

    @Test
    fun `castleKingSideBlack executes correctly`() {
        val chessBoard = ChessBoard.emptyBoard()
        val game = Game()

        // Place the king and rook
        chessBoard.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        chessBoard.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = chessBoard

        // Perform the castling move
        val result = game.blackKingsideCastling(game)

        // Check if the castling was successful
        assertThat(result).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.F, Row.EIGHT))).isInstanceOf(Rook::class.java)
        assertThat(chessBoard.getFigureAt(Position(Column.G, Row.EIGHT))).isInstanceOf(King::class.java)
    }

    @Test
    fun `castleKingSideWhite fails if king or rook not present`() {
        val chessBoard = ChessBoard.emptyBoard()
        val game = Game()

        // Place only the king
        chessBoard.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        game.board = chessBoard

        // Attempt to perform castling
        val result = game.whiteKingsideCastling(game)

        // Check if the castling was unsuccessful
        assertThat(result).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.F, Row.ONE))).isNull()
        assertThat(chessBoard.getFigureAt(Position(Column.G, Row.ONE))).isNull()
    }

    @Test
    fun `castleKingSideBlack fails if king or rook not present`() {
        val chessBoard = ChessBoard.emptyBoard()
        val game = Game()

        // Place only the king
        chessBoard.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        game.board = chessBoard

        // Attempt to perform castling
        val result = game.blackKingsideCastling(game)

        // Check if the castling was unsuccessful
        assertThat(result).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.F, Row.EIGHT))).isNull()
        assertThat(chessBoard.getFigureAt(Position(Column.G, Row.EIGHT))).isNull()
    }
}