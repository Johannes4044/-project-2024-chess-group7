package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.King
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook

class FEN {
    fun toFEN(board: Map<Position, Figure>): String {
        val fen = StringBuilder()
        for (j in 8 downTo 1) {
            var emptyCount = 0
            for (i in 'a'..'h') {
                val pos = Position(i, j)
                val fig = board[pos]
                if (fig != null) {
                    if (emptyCount > 0) {
                        fen.append(emptyCount)
                        emptyCount = 0
                    }
                    fen.append(fig.symbol())
                } else {
                    emptyCount++
                }
            }
            if (emptyCount > 0) {
                fen.append(emptyCount)
            }
            if (j > 1) {
                fen.append("/")
            }
        }
        return fen.toString()
    }

    fun fromFEN(fen: String): ChessBoard {
        val board = mutableMapOf<Position, Figure>()
        val rows = fen.split(" ")[0].split("/")
        var rowIndex = 8

        for (row in rows) {
            var colIndex = 'a'
            for (char in row) {
                when {
                    char.isDigit() -> {
                        colIndex += char.digitToInt()
                    }
                    else -> {
                        val isWhite = char.isLowerCase()
                        val figure = when (char.lowercaseChar()) {
                            'b' -> Pawn(isWhite)
                            't' -> Rook(isWhite)
                            's' -> Knight(isWhite)
                            'l' -> Bishop(isWhite)
                            'd' -> Queen(isWhite)
                            'k' -> King(isWhite)
                            else -> throw IllegalArgumentException("Ungültige Figur in FEN: $char")
                        }
                        board[Position(colIndex, rowIndex)] = figure
                        colIndex++
                    }
                }
            }
            rowIndex--
        }

        return ChessBoard(board)
    }
}