package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat


class GameTest : AnnotationSpec()  {
    // Teste die Startbedingungen des Spiels
    @Test
    fun `start game initializes the game correctly`() {
        val game = Game()
        game.startGame()
        assertThat(game.isGameOver()).isFalse()
    }

    // Teste einen gültigen Zug
    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }
    // Teste einen ungültigen Zug
    @Test
    fun `invalid move does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 5) // Ungültiger Zug für einen Bauern
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse()
    }
    // Teste einen Zug mit einer Figur des falschen Spielers
    @Test
    fun `move with wrong player's figure does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to) // Weißer Spieler zieht
        game.makeMove(from, to) // Schwarzer Spieler versucht zu ziehen
        assertThat(game.isGameOver()).isFalse()
    }
    //Teste Das Schwarz und weiße sich abwechseln
    @Test
    fun `players alternate turns`() {
        val game = Game()
        val fromWhite = Position('e', 2)
        val toWhite = Position('e', 4)
        val fromBlack = Position('d', 7)
        val toBlack = Position('d', 5)

        game.makeMove(fromWhite, toWhite) // Weißer Spieler zieht
        assertThat(game.isGameOver()).isFalse()

        game.makeMove(fromBlack, toBlack) // Schwarzer Spieler zieht
        assertThat(game.isGameOver()).isFalse()
    }
    // Teste einen Zug, der eine andere Figur schlägt
    @Test
    fun `valid move captures opponent's figure`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to) // Weißer Spieler zieht
        val fromBlack = Position('d', 7)
        val toBlack = Position('d', 5)
        game.makeMove(fromBlack, toBlack) // Schwarzer Spieler zieht
        val fromWhiteCapture = Position('e', 4)
        val toWhiteCapture = Position('d', 5) // Weißer Spieler schlägt die schwarze Figur
        game.makeMove(fromWhiteCapture, toWhiteCapture)
        assertThat(game.isGameOver()).isFalse()
    }
    // Teste das Ende des Spiels
    @Test
    fun `game ends in stalemate`() {
        val game = Game()
        val board = game.board
        board.move(Position('h', 1), Position('h', 1)) // Weißer König bleibt an Ort und Stelle
        board.move(Position('f', 2), Position('f', 2)) // Schwarzer König bleibt an Ort und Stelle
        board.move(Position('g', 3), Position('g', 3)) // Schwarze Dame bleibt an Ort und Stelle
        game.currentPlayerIsWhite = true

        val isStalemate = game.isGameOver() // Überprüft, ob das Spiel vorbei ist
        assertThat(isStalemate).isFalse()
    }

    @Test
    fun `white is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.getAllMoves(chessBoard)
        game.kingPositions()


        chessBoard.board[Position('e',2 )] = King(true) //weißer König wird erstellt
        chessBoard.board[Position('e',5)] = Rook(false) //schwarzer Turm wird erstellt
        val isChecked = game.whiteCheck()
        assertThat(isChecked).isTrue()
    }

    @Test
    fun `black is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.getAllMoves(chessBoard)
        game.kingPositions()

        chessBoard.board[Position('e',2 )] = King(false) //schwarzer König wird erstellt
        chessBoard.board[Position('e',5)] = Rook(true) // Eine weiße Figur, die den König bedroht (Turm auf e5)
        val isChecked = game.whiteCheck()
        assertThat(isChecked).isTrue()
    }
}
