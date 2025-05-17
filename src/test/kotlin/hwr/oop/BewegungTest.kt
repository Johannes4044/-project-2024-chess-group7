package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class BewegungTest : AnnotationSpec() {

    @Test
    fun `Bishop can move diagonally when path is clear`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(true)
        chessBoard.board[Position('c', 1)] = bishop
        val from = Position('c', 1)
        val to = Position('f', 4)

        assertThat(bishop.canMove(from, to, chessBoard)).isTrue()
    }

    @Test
    fun `Bishop can capture opponent piece diagonally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('c', 1)] = bishop
        chessBoard.board[Position('f', 4)] = opponentPawn
        val from = Position('c', 1)
        val to = Position('f', 4)

        assertThat(bishop.canMove(from, to, chessBoard)).isTrue()
    }
    @Test
    fun `Bishop available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val bishop = Bishop(true)
        chessBoard.board[Position('d', 4)] = bishop
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
        val bishop = Bishop(true)
        val blockingPawn = Pawn(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('d', 4)] = bishop
        chessBoard.board[Position('e', 5)] = blockingPawn
        chessBoard.board[Position('c', 3)] = opponentPawn
        val from = Position('d', 4)

        val moves = bishop.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('c', 3), // Gegnerische Figur kann geschlagen werden
            Position('e', 3), Position('f', 2), Position('g', 1),
            Position('c', 5), Position('b', 6), Position('a', 7)
        )
    }

    @Test
    fun `Pawn can move one step forward`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 3)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue()
        assertThat(chessBoard.move(from, to)).isTrue()
    }

    @Test
    fun `Pawn can move two steps forward from start position`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('a', 4)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue()
        assertThat(chessBoard.move(from, to)).isTrue()
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

        assertThat(whitePawn.canMove(from, to, chessBoard)).isTrue()
        assertThat(chessBoard.move(from, to)).isTrue()
    }

    @Test
    fun `Pawn cannot move backward`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 3)] = pawn
        val from = Position('a', 3)
        val to = Position('a', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse()
    }

    @Test
    fun `Pawn cannot move sideways`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)
        val to = Position('b', 2)

        assertThat(pawn.canMove(from, to, chessBoard)).isFalse()
    }

    @Test
    fun `Pawn gets promoted to Queen`(){
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 7)] = pawn
        val from = Position('a', 7)
        val to = Position('a', 8)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue()
        assertThat(chessBoard.move(from, to) { isWhite -> Queen(isWhite) }).isTrue()
        assertThat(chessBoard.getFigureAt(to)?.symbol()).isEqualTo("d")
    }

    @Test
    fun `Pawn gets promoted to other figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 7)] = pawn
        val from = Position('a', 7)
        val to = Position('a', 8)

        assertThat(pawn.canMove(from, to, chessBoard)).isTrue()
        assertThat(chessBoard.move(from, to) { isWhite -> Rook(isWhite) }).isTrue()
        assertThat(chessBoard.getFigureAt(to)?.symbol()).isEqualTo("t")
    }
    @Test
    fun `knight can move in L shape`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(true)
        chessBoard.board[Position('d', 4)] = knight
        val from = Position('d', 4)
        val to = Position('f', 5)

        assertThat(knight.canMove(from, to, chessBoard)).isTrue()
    }

    @Test
    fun `knight cannot move in non L shape`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(true)
        chessBoard.board[Position('d', 4)] = knight
        val from = Position('d', 4)
        val to = Position('d', 6)

        assertThat(knight.canMove(from, to, chessBoard)).isFalse()
    }

    @Test
    fun `knight cannot move to a position occupied by own piece`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(true)
        val blockingPawn = Pawn(true)
        chessBoard.board[Position('d', 4)] = knight
        chessBoard.board[Position('f', 5)] = blockingPawn
        val from = Position('d', 4)
        val to = Position('f', 5)

        assertThat(knight.canMove(from, to, chessBoard)).isFalse()
    }
    @Test
    fun `knight available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val knight = Knight(true)
        chessBoard.board[Position('d', 4)] = knight
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
        val knight = Knight(true)
        val blockingPawn = Pawn(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('d', 4)] = knight
        chessBoard.board[Position('b', 3)] = blockingPawn
        chessBoard.board[Position('f', 5)] = opponentPawn
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
        val pawn = Pawn(true)
        chessBoard.board[Position('a', 2)] = pawn
        val from = Position('a', 2)

        val moves = pawn.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('a', 3), Position('a', 4)
        )
    }

    @Test
    fun `Pawn available moves with blocking pieces`() {
        val chessBoard = ChessBoard.emptyBoard()
        val pawn = Pawn(true)
        val blockingPawn = Pawn(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('a', 2)] = pawn
        chessBoard.board[Position('a', 3)] = blockingPawn
        chessBoard.board[Position('b', 3)] = opponentPawn
        val from = Position('a', 2)

        val moves = pawn.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            Position('b', 3)
        )
    }
    @Test
    fun `Queen can move vertically`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(true)
        chessBoard.board[Position('d', 4)] = queen
        val from = Position('d', 4)
        val to = Position('d', 8)

        assertThat(queen.canMove(from, to, chessBoard)).isTrue()
    }

    @Test
    fun `Queen cannot move in invalid direction`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(true)
        chessBoard.board[Position('d', 4)] = queen
        val from = Position('d', 4)
        val to = Position('e', 6)

        assertThat(queen.canMove(from, to, chessBoard)).isFalse()
    }

    @Test
    fun `Queen cannot move if path is blocked`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(true)
        val blockingPawn = Pawn(true)
        chessBoard.board[Position('d', 4)] = queen
        chessBoard.board[Position('d', 6)] = blockingPawn
        val from = Position('d', 4)
        val to = Position('d', 8)

        assertThat(queen.canMove(from, to, chessBoard)).isFalse()
    }

    @Test
    fun `Queen can capture opponent piece`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('d', 4)] = queen
        chessBoard.board[Position('d', 6)] = opponentPawn
        val from = Position('d', 4)
        val to = Position('d', 6)

        assertThat(queen.canMove(from, to, chessBoard)).isTrue()
    }
    @Test
    fun `Queen available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val queen = Queen(true)
        chessBoard.board[Position('d', 4)] = queen
        val from = Position('d', 4)

        val moves = queen.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position('d', 1), Position('d', 2), Position('d', 3), Position('d', 5), Position('d', 6), Position('d', 7), Position('d', 8),
            // Horizontal
            Position('a', 4), Position('b', 4), Position('c', 4), Position('e', 4), Position('f', 4), Position('g', 4), Position('h', 4),
            // Diagonal
            Position('a', 1), Position('b', 2), Position('c', 3), Position('e', 5), Position('f', 6), Position('g', 7), Position('h', 8),
            Position('a', 7), Position('b', 6), Position('c', 5), Position('e', 3), Position('f', 2), Position('g', 1)
        )
    }
    @Test
    fun `Rook can move vertically`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        chessBoard.board[Position('d', 4)] = rook
        val from = Position('d', 4)
        val to = Position('d', 8)

        assertThat(rook.canMove(from, to, chessBoard)).isTrue()
    }

    @Test
    fun `Rook can move horizontally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        chessBoard.board[Position('d', 4)] = rook
        val from = Position('d', 4)
        val to = Position('a', 4)

        assertThat(rook.canMove(from, to, chessBoard)).isTrue()
    }

    @Test
    fun `Rook cannot move diagonally`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        chessBoard.board[Position('d', 4)] = rook
        val from = Position('d', 4)
        val to = Position('e', 5)

        assertThat(rook.canMove(from, to, chessBoard)).isFalse()
    }
    @Test
    fun `Rook cannot move if path is blocked`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        val blockingPawn = Pawn(true)
        chessBoard.board[Position('d', 4)] = rook
        chessBoard.board[Position('d', 6)] = blockingPawn
        val from = Position('d', 4)
        val to = Position('d', 8)

        assertThat(rook.canMove(from, to, chessBoard)).isFalse()
    }
    @Test
    fun `Rook can capture opponent piece`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        val opponentPawn = Pawn(false)
        chessBoard.board[Position('d', 4)] = rook
        chessBoard.board[Position('d', 6)] = opponentPawn
        val from = Position('d', 4)
        val to = Position('d', 6)

        assertThat(rook.canMove(from, to, chessBoard)).isTrue()
    }
    @Test
    fun `Rook available moves on empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = Rook(true)
        chessBoard.board[Position('d', 4)] = rook
        val from = Position('d', 4)

        val moves = rook.availableMoves(from, chessBoard)
        assertThat(moves).containsExactlyInAnyOrder(
            // Vertikal
            Position('d', 1), Position('d', 2), Position('d', 3), Position('d', 5), Position('d', 6), Position('d', 7), Position('d', 8),
            // Horizontal
            Position('a', 4), Position('b', 4), Position('c', 4), Position('e', 4), Position('f', 4), Position('g', 4), Position('h', 4)
        )
    }
    // Move Test
    @Test
    fun `isValid returns true for valid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val pawn = Pawn(true)
        board.board[from] = pawn
        val move = Move(from, to, board)
        assertThat(move.isValid()).isTrue()
    }

    @Test
    fun `isValid returns false for invalid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 5)
        val pawn = Pawn(true)
        board.board[from] = pawn
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
        val whitePawn = Pawn(true)
        val blackPawn = Pawn(false)
        board.board[from] = whitePawn
        board.board[to] = blackPawn
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isTrue()
    }

    @Test
    fun `isCapture returns false if target is same color`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn1 = Pawn(true)
        val whitePawn2 = Pawn(true)
        board.board[from] = whitePawn1
        board.board[to] = whitePawn2
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `isCapture returns false if target is empty`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val whitePawn = Pawn(true)
        board.board[from] = whitePawn
        val move = Move(from, to, board)
        assertThat(move.isCapture()).isFalse()
    }

    @Test
    fun `execute returns true for valid move`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val pawn = Pawn(true)
        board.board[from] = pawn
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
        val pawn = Pawn(true)
        board.board[from] = pawn
        val move = Move(from, to, board)
        assertThat(move.execute()).isFalse()
        assertThat(board.getFigureAt(from)).isEqualTo(pawn)
        assertThat(board.getFigureAt(to)).isNull()
    }

    // Position Test

    @Test
    fun `Position erstellt korrektes Objekt`() {
        val pos = Position('e', 4)
        assertThat(pos.column).isEqualTo('e')
        assertThat(pos.row).isEqualTo(4)
    }

    @Test
    fun `Position equals und hashCode funktionieren`() {
        val pos1 = Position('a', 1)
        val pos2 = Position('a', 1)
        val pos3 = Position('b', 2)
        assertThat(pos1).isEqualTo(pos2)
        assertThat(pos1.hashCode()).isEqualTo(pos2.hashCode())
        assertThat(pos1).isNotEqualTo(pos3)
    }

    @Test
    fun `toString gibt korrektes Format back`() {
        val pos = Position('d', 5)
        assertThat(pos.toString()).isEqualTo("d5")
    }

    @Test
    fun `toSring for Move gives is correct format`() {
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('e', 3)
        val move = Move(from, to, board)
        assertThat(move.toString()).isEqualTo("Zug von e2 nach e3")
    }

    @Test
    fun `isCapture output is correct`(){
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val to = Position('f', 3)
        val whitePawn = Pawn(true)
        val blackPawn = Pawn(false)
        board.board[from] = whitePawn
        board.board[to] = blackPawn

        val move = Move(from, to, board)


        val outputStream = ByteArrayOutputStream()
        val originalOut = System.out
        val printStream = PrintStream(outputStream)
        System.setOut(printStream)

        try {
            move.execute()
        }
        finally {
            System.setOut(originalOut)
        }


        val output = outputStream.toString()
        assertThat(output).contains("Figur wurde geschlagen!")
    }

    @Test
    fun`isCapture returns True`(){
        val board = ChessBoard.emptyBoard()
        val from = Position('e', 2)
        val from2 = Position('a', 3)
        val to = Position('f', 3)
        val to2 = Position('a',4)
        val whitePawn = Pawn(true)
        val whitePawn2 = Pawn(true)
        val blackPawn = Pawn(false)
        board.board[from] = whitePawn
        board.board[to] = blackPawn
        board.board[from2] = whitePawn2

        val move = Move(from, to, board)
        val move2 = Move(from2, to2, board)

        assertThat(move.isCapture()).isTrue()
        assertThat(move2.isCapture()).isFalse()
    }
}


