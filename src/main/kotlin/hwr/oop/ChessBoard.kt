package hwr.oop
import hwr.oop.figures.*


class ChessBoard(private val board: MutableMap<Position, Figure>) {

    companion object {
        fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

        fun fullBoard(): ChessBoard {
            val board = mutableMapOf<Position, Figure>()
            for (i in Column.values()) {
                board[Position(i, Row.TWO)] = Pawn(Color.WHITE)
            }
            for (i in Column.values()) {
                board[Position(i, Row.SEVEN)] = Pawn(Color.BLACK)
            }
            board[Position(Column.A, Row.ONE)] = Rook(Color.WHITE)
            board[Position(Column.A, Row.EIGHT)] = Rook(Color.BLACK)
            board[Position(Column.H, Row.ONE)] = Rook(Color.WHITE)
            board[Position(Column.H, Row.EIGHT)] = Rook(Color.BLACK)

            board[Position(Column.B, Row.ONE)] = Knight(Color.WHITE)
            board[Position(Column.B, Row.EIGHT)] = Knight(Color.BLACK)
            board[Position(Column.G, Row.ONE)] = Knight(Color.WHITE)
            board[Position(Column.G, Row.EIGHT)] = Knight(Color.BLACK)

            board[Position(Column.C, Row.ONE)] = Bishop(Color.WHITE)
            board[Position(Column.C, Row.EIGHT)] = Bishop(Color.BLACK)
            board[Position(Column.F, Row.ONE)] = Bishop(Color.WHITE)
            board[Position(Column.F, Row.EIGHT)] = Bishop(Color.BLACK)

            board[Position(Column.D, Row.ONE)] = Queen(Color.WHITE)
            board[Position(Column.D, Row.EIGHT)] = Queen(Color.BLACK)

            board[Position(Column.E, Row.ONE)] = King(Color.WHITE)
            board[Position(Column.E, Row.EIGHT)] = King(Color.BLACK)

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