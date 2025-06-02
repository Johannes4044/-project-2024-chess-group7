package hwr.oop

import hwr.oop.figures.King
import hwr.oop.figures.Pawn
import hwr.oop.figures.Rook
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat


class GameTest : AnnotationSpec() {
    @Test
    fun `start game initializes the game correctly`() {
        val game = Game()
        game.startGame()
        val board = ChessBoard.fullBoard()
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `whiteCheck returns true if black move threatens white king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 1), King(Color.WHITE))
        board.placePieces(Position('e', 8), Rook(Color.BLACK))
        game.board = board
        assertThat(game.whiteCheck()).isTrue()
    }
    @Test
    fun `blackCheck returns false if there is no black king on the board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.blackCheck()).isFalse()
    }

    @Test
    fun `blackCheck returns true if white move threatens black king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position('e', 1), King(Color.BLACK))
        board.placePieces(Position('e', 8), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }
    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to, null)
        assertThat(game.isGameOver()).isFalse()
    }
    @Test
    fun `currentPlayerIsWhite changes after valid move`() {
        val game = Game()
        val startValue = game.currentPlayerIsWhite
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to)
        assertThat(game.currentPlayerIsWhite).isNotEqualTo(startValue)
    }

    @Test
    fun `whiteCheck returns false if there is no white king on the board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.whiteCheck()).isFalse()
    }

    @Test
    fun `invalid move does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 5)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `move with wrong player's figure does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `players alternate turns`() {
        val game = Game()
        val fromWhite = Position('e', 2)
        val toWhite = Position('e', 4)
        val fromBlack = Position('d', 7)
        val toBlack = Position('d', 5)

        game.makeMove(fromWhite, toWhite)
        assertThat(game.isGameOver()).isFalse()

        game.makeMove(fromBlack, toBlack)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `valid move captures opponent's figure`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to)
        val fromBlack = Position('d', 7)
        val toBlack = Position('d', 5)
        game.makeMove(fromBlack, toBlack)
        val fromWhiteCapture = Position('e', 4)
        val toWhiteCapture = Position('d', 5)
        game.makeMove(fromWhiteCapture, toWhiteCapture)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `game ends in stalemate`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        board.move(Position('h', 1), Position('h', 1))
        board.move(Position('f', 2), Position('f', 2))
        board.move(Position('g', 3), Position('g', 3))
        game.currentPlayerIsWhite = true

        val isStalemate = game.isGameOver()
        assertThat(isStalemate).isFalse()
    }

    @Test
    fun `getAllMoves return all available moves back`() {
        val game = Game()
        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        assertThat(whiteMoves).isNotEmpty
        assertThat(blackMoves).isNotEmpty
    }

    @Test
    fun `white is checked`() {
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board = chessBoard
        chessBoard.placePieces(Position('e', 2), King(Color.WHITE))
        chessBoard.placePieces(Position('e', 5), Rook(Color.BLACK))
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `black is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board  = chessBoard

        chessBoard.placePieces(Position('e', 2), King(Color.BLACK))
        chessBoard.placePieces(Position('e', 5), Rook(Color.WHITE))
        assertThat(game.blackCheck()).isTrue()
    }
    @Test
    fun `getAllFigures return all white figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val whiteFigures = chessBoard.getAllFigures(true) as List<*>
        assertThat(whiteFigures).hasSize(16)
        assertThat(whiteFigures.all { (it as Figure).color == Color.WHITE }).isTrue()    }

    @Test
    fun `getAllFigures return all black figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val blackFigures = chessBoard.getAllFigures(false) as List<*>
        assertThat(blackFigures).hasSize(16)
        assertThat(blackFigures.all { (it as Figure).color == Color.BLACK }).isTrue()}

    @Test
    fun `getAllPositions return all occupied positions back`() {
        val chessBoard = ChessBoard.fullBoard()
        val positions = chessBoard.getAllPositions() as List<*>
        assertThat(positions).hasSize(32)
        assertThat(positions.all { it is Position }).isTrue()
    }

    @Test
    fun `move from invalid position returns false`() {
        val game = Game()
        val from = Position('z', 9)
        val to = Position('a', 1)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `move from empty position returns false`() {
        val game = Game()
        val from = Position('a', 3)
        val to = Position('a', 4)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `move to own figure returns false`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('d', 1)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `makeMove returns false, if move is not valid`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.board.placePieces(Position('a', 7), Pawn(Color.WHITE))
        game.currentPlayerIsWhite = false
        val result = game.makeMove(Position('a', 7), Position('a', 8), FigureType.Queen)
        assertThat(result).isFalse()
    }

    @Test
    fun `game is over when no king on board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `move outside board returns false`() {
        val game = Game()
        val from = Position('i', 9)
        val to = Position('j', 10)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `cannot move if game is over`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
        val result = game.makeMove(Position('a', 1), Position('a', 2))
        assertThat(result).isFalse()
    }
    @Test
    fun `game ends after 50 moves without pawn move or captur`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Platziere nur Türme und Könige für einfache Züge
        board.placePieces(Position('a', 1), King(Color.WHITE))
        board.placePieces(Position('h', 1), Rook(Color.WHITE))
        board.placePieces(Position('a', 8), King(Color.BLACK))
        board.placePieces(Position('h', 8), Rook(Color.BLACK))

        game.board = board

        // Führe 50 Züge aus
        repeat(25) {
            game.makeMove(Position('h', 1), Position('h', 2))
            game.makeMove(Position('h', 8), Position('h', 7))
            game.makeMove(Position('h', 2), Position('h', 1))
            game.makeMove(Position('h', 7), Position('h', 8))
        }

        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `game keeps going after over 50 moves if a pawn moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Platziere Figuren
        board.placePieces(Position('a', 1), King(Color.WHITE))
        board.placePieces(Position('e', 2), Pawn(Color.WHITE))
        board.placePieces(Position('a', 8), King(Color.BLACK))
        board.placePieces(Position('h', 8), Rook(Color.BLACK))

        game.board = board

        // Mache 48 Züge
        repeat(24) {
            game.makeMove(Position('a', 1), Position('b', 1))
            game.makeMove(Position('h', 8), Position('h', 7))
            game.makeMove(Position('b', 1), Position('a', 1))
            game.makeMove(Position('h', 7), Position('h', 8))
        }

        // Führe Bauernzug aus
        game.makeMove(Position('e', 2), Position('e', 3))

        assertThat(game.totalMoves).isEqualTo(0)
        assertThat(game.isGameOver()).isFalse()
    }

}
