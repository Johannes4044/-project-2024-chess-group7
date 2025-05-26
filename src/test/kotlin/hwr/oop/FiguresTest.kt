import hwr.oop.ChessBoard
import hwr.oop.Position
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
                row("Pawn", true, "b"),
                row("Pawn", false, "B"),
                row("Rook", true, "t"),
                row("Rook", false, "T"),
                row("Knight", true, "s"),
                row("Knight", false, "S"),
                row("Bishop", true, "l"),
                row("Bishop", false, "L"),
                row("Queen", true, "d"),
                row("Queen", false, "D"),
                row("King", true, "k"),
                row("King", false, "K")
            ) { figureType: String, isWhite: Boolean, expectedSymbol: String ->
                val figure = when (figureType) {
                    "Pawn" -> Pawn(isWhite)
                    "Rook" -> Rook(isWhite)
                    "Knight" -> Knight(isWhite)
                    "Bishop" -> Bishop(isWhite)
                    "Queen" -> Queen(isWhite)
                    "King" -> King(isWhite)
                    else -> throw IllegalArgumentException("Unknown figure type")
                }
                figure.symbol() shouldBe expectedSymbol
                figure.isWhite shouldBe isWhite
            }
        }
    test("King can move if destination is empty") {
        val king = King(true)
        val from = Position('e', 1)
        val possibleMoves = listOf(
            Position('e', 2),
            Position('d', 1),
            Position('f', 1),
            Position('d', 2),
            Position('f', 2),
        )
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val moves = king.availableMoves(from, board)

        possibleMoves.forEach { to ->
            moves shouldContain to
        }
    }

    test("King cannot move more than one square in any direction") {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val invalidMoves = listOf(
            Position('e', 6),
            Position('g', 4),
            Position('c', 4),
            Position('e', 2),
            Position('g', 6)
        )

        val moves = king.availableMoves(from, board)

        invalidMoves.forEach { to ->
            moves shouldNotContain to
        }
    }

    test("King cannot move to a square occupied by same color") {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)
        val friendly = King(true)
        val to = Position('e', 5)
        board.placePieces(to, friendly)

        val moves = king.availableMoves(from, board)

        moves shouldNotContain to
    }

    test("King can capture enemy piece") {
        val king = King(true)
        val from = Position('e', 4)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)
        val enemy = King(false)
        val to = Position('e', 5)
        board.placePieces(to, enemy)

        val moves = king.availableMoves(from, board)

        moves shouldContain to
    }

    test("availableMoves returns only valid squares") {
        val king = King(true)
        val from = Position('a', 1)
        val board = ChessBoard.emptyBoard()
        board.placePieces(from, king)

        val moves = king.availableMoves(from, board)
        moves.forEach {
            (it.column in 'a'..'h' && it.row in 1..8).shouldBeTrue()
        }
    }
})