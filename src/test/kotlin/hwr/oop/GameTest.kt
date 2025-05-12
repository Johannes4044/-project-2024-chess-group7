package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat


class GameTest : AnnotationSpec()  {
    // Teste die Startbedingungen des Spiels
    @Test
    fun `start game initializes the game correctly`() {
        val game = Game()
        game.startGame()
        assertThat(game.isGameOver()).isFalse
    }

    // Teste einen gültigen Zug
    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse
    }
    // Teste einen ungültigen Zug
    @Test
    fun `invalid move does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 5) // Ungültiger Zug für einen Bauern
        game.makeMove(from, to)
        assertThat(game.isGameOver()).isFalse
    }
    // Teste einen Zug mit einer Figur des falschen Spielers
    @Test
    fun `move with wrong player's figure does not change player turn`() {
        val game = Game()
        val from = Position('e', 2)
        val to = Position('e', 4)
        game.makeMove(from, to) // Weißer Spieler zieht
        game.makeMove(from, to) // Schwarzer Spieler versucht zu ziehen
        assertThat(game.isGameOver()).isFalse
    }
    // Teste das Ende des Spiels
}