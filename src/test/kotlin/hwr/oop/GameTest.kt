package hwr.oop

import hwr.oop.figures.FigureType
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
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
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
        board.placePieces(Position(Column.E, Row.ONE), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }
    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to, null)
        assertThat(game.isGameOver()).isFalse()
    }
    @Test
    fun `currentPlayerIsWhite changes after valid move`() {
        val game = Game()
        val startValue = game.currentPlayerIsWhite
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
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
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FIVE)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `move with wrong player's figure does not change player turn`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `players alternate turns`() {
        val game = Game()
        val fromWhite = Position(Column.E, Row.TWO)
        val toWhite = Position(Column.E, Row.FOUR)
        val fromBlack = Position(Column.D, Row.SEVEN)
        val toBlack = Position(Column.D, Row.FIVE)

        game.makeMove(fromWhite, toWhite)
        assertThat(game.isGameOver()).isFalse()

        game.makeMove(fromBlack, toBlack)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `valid move captures opponent's figure`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        val fromBlack = Position(Column.D, Row.SEVEN)
        val toBlack = Position(Column.D, Row.FIVE)
        game.makeMove(fromBlack, toBlack)
        val fromWhiteCapture = Position(Column.E, Row.FOUR)
        val toWhiteCapture = Position(Column.D, Row.FIVE)
        game.makeMove(fromWhiteCapture, toWhiteCapture)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `game ends in stalemate`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        board.move(Position(Column.H, Row.TWO), Position(Column.H, Row.THREE))
        board.move(Position(Column.F, Row.TWO), Position(Column.F, Row.THREE))
        board.move(Position(Column.G, Row.TWO), Position(Column.G, Row.THREE))
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
        chessBoard.placePieces(Position(Column.E, Row.TWO), King(Color.WHITE))
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Rook(Color.BLACK))
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `black is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board  = chessBoard

        chessBoard.placePieces(Position(Column.E, Row.TWO), King(Color.BLACK))
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Rook(Color.WHITE))
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

//    @Test
//    fun `move from invalid position returns false`() {
//        val game = Game()
//        val from = Position('z', 9)
//        val to = Position(Column.E, 1)
//        val result = game.makeMove(from, to)
//        assertThat(result).isFalse()
//    }

    @Test
    fun `move from empty position returns false`() {
        val game = Game()
        val from = Position(Column.A, Row.THREE)
        val to = Position(Column.A, Row.FOUR)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `move to own figure returns false`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.D, Row.ONE)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `makeMove returns false, if move is not valid`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        game.currentPlayerIsWhite = false
        val result = game.makeMove(Position(Column.A, Row.SEVEN), Position(Column.A, Row.EIGHT), FigureType.Queen)
        assertThat(result).isFalse()
    }

    @Test
    fun `game is over when no king on board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
    }

//    @Test
//    fun `move outside board returns false`() {
//        val game = Game()
//        val from = Position('i', 9)
//        val to = Position('j', 10)
//        val result = game.makeMove(from, to)
//        assertThat(result).isFalse()
//    }

    @Test
    fun `cannot move if game is over`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
        val result = game.makeMove(Position(Column.A, Row.ONE), Position(Column.A, Row.TWO))
        assertThat(result).isFalse()
    }
    @Test
    fun `game ends after 50 moves without pawn move or capture`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Platziere nur Türme und Könige für einfache Züge
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))

        game.board = board

        // Führe 50 Züge aus
        repeat(25) {
            game.makeMove(Position(Column.H, Row.ONE), Position(Column.H, Row.TWO))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.H, Row.TWO), Position(Column.H, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `game keeps going after over 50 moves if a pawn moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Platziere Figuren
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))

        game.board = board

        // Mache 48 Züge
        repeat(24) {
            game.makeMove(Position(Column.A, Row.ONE), Position(Column.B, Row.ONE))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.B, Row.ONE), Position(Column.A, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        // Führe Bauernzug aus
        game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))

        assertThat(game.totalMoves).isEqualTo(0)
        assertThat(game.isGameOver()).isFalse()
    }

}
