package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.King
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook
import kotlin.text.iterator

class FEN {
    fun toFEN(board: ChessBoard): String {
        val fen = StringBuilder()
        for (j in 8 downTo 1) {
            var emptyCount = 0
            for (i in 'a'..'h') {
                val pos = Position.valueOf("${i.uppercaseChar()}$j")
                val fig = board.getFigureAt(pos)
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
                            'b' -> Pawn(if (isWhite) Color.WHITE else Color.BLACK)
                            't' -> Rook(if (isWhite) Color.WHITE else Color.BLACK)
                            's' -> Knight(if (isWhite) Color.WHITE else Color.BLACK)
                            'l' -> Bishop(if (isWhite) Color.WHITE else Color.BLACK)
                            'd' -> Queen(if (isWhite) Color.WHITE else Color.BLACK)
                            'k' -> King(if (isWhite) Color.WHITE else Color.BLACK)
                            else -> throw IllegalArgumentException("Ungültige Figur in FEN: $char")
                        }
                        board[Position.valueOf("${colIndex.uppercaseChar()}$rowIndex")] = figure
                        colIndex++
                    }
                }
            }
            rowIndex--
        }

        return ChessBoard(board)
    }
}