package hwr.oop

import hwr.oop.figures.*


class ChessBoard(private val board: MutableMap<Position, Figure>) {

    companion object {
        fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

        fun fullBoard(): ChessBoard {
            val board = mutableMapOf<Position, Figure>()
            for (i in 'a'..'h') {
                board[Position(i, 2)] = Pawn(true)
            }
            for (i in 'a'..'h') {
                board[Position(i, 7)] = Pawn(false)
            }
            board[Position('a', 1)] = Rook(true)
            board[Position('a', 8)] = Rook(false)
            board[Position('h', 1)] = Rook(true)
            board[Position('h', 8)] = Rook(false)

            board[Position('b', 1)] = Knight(true)
            board[Position('b', 8)] = Knight(false)
            board[Position('g', 1)] = Knight(true)
            board[Position('g', 8)] = Knight(false)

            board[Position('c', 1)] = Bishop(true)
            board[Position('c', 8)] = Bishop(false)
            board[Position('f', 1)] = Bishop(true)
            board[Position('f', 8)] = Bishop(false)

            board[Position('d', 1)] = Queen(true)
            board[Position('d', 8)] = Queen(false)

            board[Position('e', 1)] = King(true)
            board[Position('e', 8)] = King(false)

            return ChessBoard(board)
        }

        fun fromFEN(fenString: String): ChessBoard {
            return FEN().fromFEN(fenString)
        }
    }

    fun getFigureAt(position: Position): Figure? = board[position]

    fun move(from: Position, to: Position, promoteTo: ((Boolean) -> Figure)? = null): Boolean {
        val figure = board[from] ?: return false
        if (figure.availableMoves(from, this).contains(to)) {
            board.remove(from)
            board[to] = figure
            return true

    } else {
        println("Ungültiger Zug für ${figure.symbol()} von $from nach $to")
        return false
    }
    }
    fun promoteFigure(position: Position, promoteTo: FigureType?): Boolean {
        val figure = board[position] ?: return false
        if (figure is Pawn) {
            board[position] = when (promoteTo){
                FigureType.Rook -> Rook(figure.isWhite)
                FigureType.Knight -> Knight(figure.isWhite)
                FigureType.Bishop -> Bishop(figure.isWhite)
                FigureType.Queen -> Queen(figure.isWhite)
                else -> Queen(figure.isWhite)
            }
            return true
        }
        return false
    }
    fun displayBoard() {
        for (j in 8 downTo 1) {
            for (i in 'a'..'h') {
                val pos = Position(i, j)
                val fig = board[pos]
                if (fig != null) {
                    print(fig.symbol() + " ")
                } else {
                    print(". ")
                }
            }
            println()
        }
    }

    fun placePieces(position: Position, figure: Figure) {
        board[position] = figure
    }

    fun getAllFigures(whiteTurn: Boolean): Any {

        val allFigures = mutableListOf<Figure>()
        for (entry in board.entries) {
            if (entry.value.isWhite == whiteTurn) {
                allFigures.add(entry.value)
            }
        }
        return allFigures
    }

    fun findKing(whiteTurn: Boolean): Position? {
        for (entry in board.entries) {
            if (entry.value is King && (entry.value as King).isWhite == whiteTurn) {
                return entry.key
            }
        }
        return null
    }

    fun getAllPositions(): Any {
        val allPositions = mutableListOf<Position>()
        for (entry in board.entries) {
            allPositions.add(entry.key)
        }
        return allPositions
    }

    fun isSpaceFree(Game: Game, Position: Position, isWhiteCastling: Boolean): Boolean {
        val (whiteMoves, blackMoves) = Game.getAllMoves(this)
        if (whiteMoves.contains(Position) && isWhiteCastling) return false
        if (blackMoves.contains(Position) && !isWhiteCastling) return false
        return true
    }
}
