package hwr.oop
import hwr.oop.figures.*


class ChessBoard(private val board: MutableMap<Position, Figure>) {

    companion object {
        fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

        fun fullBoard(): ChessBoard {
            val board = mutableMapOf<Position, Figure>()
            for (i in 'a'..'h') {
                board[Position(i, 2)] = Pawn(Color.WHITE)
            }
            for (i in 'a'..'h') {
                board[Position(i, 7)] = Pawn(Color.BLACK)
            }
            board[Position('a', 1)] = Rook(Color.WHITE)
            board[Position('a', 8)] = Rook(Color.BLACK)
            board[Position('h', 1)] = Rook(Color.WHITE)
            board[Position('h', 8)] = Rook(Color.BLACK)

            board[Position('b', 1)] = Knight(Color.WHITE)
            board[Position('b', 8)] = Knight(Color.BLACK)
            board[Position('g', 1)] = Knight(Color.WHITE)
            board[Position('g', 8)] = Knight(Color.BLACK)

            board[Position('c', 1)] = Bishop(Color.WHITE)
            board[Position('c', 8)] = Bishop(Color.BLACK)
            board[Position('f', 1)] = Bishop(Color.WHITE)
            board[Position('f', 8)] = Bishop(Color.BLACK)

            board[Position('d', 1)] = Queen(Color.WHITE)
            board[Position('d', 8)] = Queen(Color.BLACK)

            board[Position('e', 1)] = King(Color.WHITE)
            board[Position('e', 8)] = King(Color.BLACK)

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
        error("Ungültiger Zug von ${figure.symbol()} von $from nach $to")
        
        return false
    }

    }

    fun promoteFigure(position: Position, promoteTo: FigureType?): Boolean {
        val figure = board[position] ?: return false
        if (figure is Pawn) {
            board[position] = when (promoteTo) {
                FigureType.Rook -> Rook(figure.color)
                FigureType.Knight -> Knight(figure.color)
                FigureType.Bishop -> Bishop(figure.color)
                FigureType.Queen -> Queen(figure.color)
                else -> Queen(figure.color)
            }
            return true
        }
        return false
    }

    fun placePieces(position: Position, figure: Figure) {
        board[position] = figure
    }

    fun getAllFigures(whiteTurn: Boolean): Any {

        val allFigures = mutableListOf<Figure>()
        for (entry in board.entries) {
            if (entry.value.color == Color.WHITE == whiteTurn) {
                allFigures.add(entry.value)
            }
        }
        return allFigures
    }

    fun findKing(whiteTurn: Boolean): Position? {
        for (entry in board.entries) {
            if (entry.value is King && (entry.value as King).color == Color.WHITE == whiteTurn) {
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