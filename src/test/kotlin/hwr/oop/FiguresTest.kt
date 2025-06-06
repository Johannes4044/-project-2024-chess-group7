
package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.King
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook
import io.kotest.core.spec.style.FunSpec

import io.kotest.data.forAll
import io.kotest.data.row

import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe

class FigureSymbolTest : FunSpec({
    test("figure symbol and color are correct") {
        forAll(
            row("Pawn", Color.WHITE, "b"),
            row("Pawn", Color.BLACK, "B"),
            row("Rook", Color.WHITE, "t"),
            row("Rook", Color.BLACK, "T"),
            row("Knight", Color.WHITE, "s"),
            row("Knight", Color.BLACK, "S"),
            row("Bishop", Color.WHITE, "l"),
            row("Bishop", Color.BLACK, "L"),
            row("Queen", Color.WHITE, "d"),
            row("Queen", Color.BLACK, "D"),
            row("King", Color.WHITE, "k"),
            row("King", Color.BLACK, "K")
        ) { figureType: String, color: Color, expectedSymbol: String ->
            val figure = when (figureType) {
                "Pawn" -> Pawn(color)
                "Rook" -> Rook(color)
                "Knight" -> Knight(color)
                "Bishop" -> Bishop(color)
                "Queen" -> Queen(color)
                "King" -> King(color)
                else -> throw IllegalArgumentException("Unknown figure type")
            }
            figure.symbol() shouldBe expectedSymbol
            figure.color() shouldBe color
        }
    }
    test("King can move if destination is empty") {
        val king = King(Color.WHITE)
        val from = Position(Column.E, Row.ONE)
        val possibleMoves = listOf(
            Position(Column.E, Row.TWO),
            Position(Column.D, Row.ONE),
            Position(Column.F, Row.ONE),
            Position(Column.D, Row.TWO),
            Position(Column.F, Row.TWO),
        )
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val moves = king.availableTargets(from, board)
        possibleMoves.forEach { to ->
            moves shouldContain to
        }
    }

    test("King cannot move more than one square in any direction") {
        val king = King(Color.WHITE)
        val from = Position(Column.E, Row.FOUR)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val invalidMoves = listOf(
            Position(Column.E, Row.SIX),
            Position(Column.G, Row.FOUR),
            Position(Column.C, Row.FOUR),
            Position(Column.E, Row.TWO),
            Position(Column.G, Row.SIX)
        )

        val moves = king.availableTargets(from, board)

        invalidMoves.forEach { to ->
            moves shouldNotContain to
        }
    }

    test("King cannot move to a square occupied by same color") {
        val king = King(Color.WHITE)
        val from = Position(Column.E, Row.FOUR)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)
        val friendly = King(Color.WHITE)
        val to = Position(Column.E, Row.FIVE)
        board.placePieces(to, friendly)

        val moves = king.availableTargets(from, board)

        moves shouldNotContain to
    }

    test("King can capture enemy piece") {
        val king = King(Color.WHITE)
        val from = Position(Column.E, Row.FOUR)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)
        val enemy = King(Color.BLACK)
        val to = Position(Column.E, Row.FIVE)
        board.placePieces(to, enemy)

        val moves = king.availableTargets(from, board)

        moves shouldContain to
    }

    test("availableTargets returns only valid squares") {
        val king = King(Color.WHITE)
        val from = Position(Column.A, Row.ONE)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val moves = king.availableTargets(from, board)
        moves.forEach {
            (it.column in Column.values() && it.row in Row.values()).shouldBeTrue()
        }
    }
})