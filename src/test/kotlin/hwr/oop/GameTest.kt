package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat


class GameTest : AnnotationSpec() {
    @Test
    fun `start game initializes the game correctly`() {
        val game = Game()
        game.startGame()
        assertThat(game.isGameOver()).isFalse()
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
        chessBoard.placePieces(Position('e', 2), King(true))
        chessBoard.placePieces(Position('e', 5), Rook(false))
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `black is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board  = chessBoard

        chessBoard.placePieces(Position('e', 2), King(false))
        chessBoard.placePieces(Position('e', 5), Rook(true))
        assertThat(game.blackCheck()).isTrue()
    }
    @Test
    fun `getAllFigures return all white figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val whiteFigures = chessBoard.getAllFigures(true) as List<*>
        assertThat(whiteFigures).hasSize(16)
        assertThat(whiteFigures.all { (it as Figure).isWhite }).isTrue()
    }

    @Test
    fun `getAllFigures return all black figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val blackFigures = chessBoard.getAllFigures(false) as List<*>
        assertThat(blackFigures).hasSize(16)
        assertThat(blackFigures.all { !(it as Figure).isWhite }).isTrue()
    }

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
    fun `pawn promotion works`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        game.board.placePieces(Position('a', 7), Pawn(true))
        game.currentPlayerIsWhite = true
        val result = game.makeMove(Position('a', 7), Position('a', 8))
        assertThat(result).isTrue()
        assertThat(game.board.getFigureAt(Position('a', 8))?.symbol()).isEqualTo("d")
    }

    @Test
    fun `game is over when no king on board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `zug history`(){
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e',4)
        val figure = game.board.getFigureAt(from)
        val listTest = listOf (
            Triple(figure, from, to),
        )
        game.makeMove(from,to)
        val history = game.moves
        assertThat(history).isEqualTo(listTest)
    }


}
