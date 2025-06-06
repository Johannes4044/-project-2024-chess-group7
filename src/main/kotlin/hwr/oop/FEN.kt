package hwr.oop

import hwr.oop.figures.Bishop
import hwr.oop.figures.King
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook

class FEN {
    fun toFEN(board: ChessBoard): String {
        val fen = StringBuilder()
        for (j in Row.values().reversed()) {
            var emptyCount = 0
            for (i in Column.values()) {
                val pos = Position(i, j)
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
            if (j > Row.ONE) {
                fen.append("/")
            }
        }
        return fen.toString()
    }

    fun fromFEN(fen: String): ChessBoard {
        val board = mutableMapOf<Position, Figure>()
        val rows = fen.split(" ")[0].split("/")
        var rowIndex = Row.EIGHT

        for (row in rows) {
            var colIndex = Column.A
            for (char in row) {
                when {
                    char.isDigit() -> {
                        val skip = char.digitToInt()
                        if (colIndex.ordinal + skip < Column.values().size) {
                            colIndex = Column.values()[colIndex.ordinal + skip]
                        }
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
                        board[Position(colIndex, rowIndex)] = figure
                        if (colIndex.ordinal + 1 < Column.values().size) {
                            colIndex = Column.values()[colIndex.ordinal + 1]
                        }
                    }
                }
            }
            if (rowIndex.ordinal - 1 >= 0) {
                rowIndex = Row.values()[rowIndex.ordinal - 1]
            }
        }
        return ChessBoard(board)
    }
}