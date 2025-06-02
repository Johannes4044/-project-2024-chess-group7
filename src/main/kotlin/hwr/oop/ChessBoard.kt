package hwr.oop
import hwr.oop.figures.*

class ChessBoard(private val board: MutableMap<Position, Figure>) {

    companion object {
        fun emptyBoard(): ChessBoard = ChessBoard(mutableMapOf())

        fun fullBoard(): ChessBoard {
            val board = mutableMapOf<Position, Figure>()
            for (i in 'a'..'h') {
                board[Position.from(i, 2)!!] = Pawn(Color.WHITE)
            }
            for (i in 'a'..'h') {
                board[Position.from(i, 7)!!] = Pawn(Color.BLACK)
            }
            board[Position.from('a', 1)!!] = Rook(Color.WHITE)
            board[Position.from('a', 8)!!] = Rook(Color.BLACK)
            board[Position.from('h', 1)!!] = Rook(Color.WHITE)
            board[Position.from('h', 8)!!] = Rook(Color.BLACK)

            board[Position.from('b', 1)!!] = Knight(Color.WHITE)
            board[Position.from('b', 8)!!] = Knight(Color.BLACK)
            board[Position.from('g', 1)!!] = Knight(Color.WHITE)
            board[Position.from('g', 8)!!] = Knight(Color.BLACK)

            board[Position.from('c', 1)!!] = Bishop(Color.WHITE)
            board[Position.from('c', 8)!!] = Bishop(Color.BLACK)
            board[Position.from('f', 1)!!] = Bishop(Color.WHITE)
            board[Position.from('f', 8)!!] = Bishop(Color.BLACK)

            board[Position.from('d', 1)!!] = Queen(Color.WHITE)
            board[Position.from('d', 8)!!] = Queen(Color.BLACK)

            board[Position.from('e', 1)!!] = King(Color.WHITE)
            board[Position.from('e', 8)!!] = King(Color.BLACK)

            return ChessBoard(board)
        }

//        fun fromFEN(fenString: String): ChessBoard {
//            return FEN().fromFEN(fenString)
//        }
    }

    fun getFigureAt(position: Position): Figure? = board[position]

    fun move(from: Position, to: Position, promoteTo: ((Boolean) -> Figure)? = null): Boolean {
        val figure = board[from] ?: return false
        if (figure.availableTargets(from, this).contains(to)) {
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
                FigureType.Rook -> Rook(figure.color())
                FigureType.Knight -> Knight(figure.color())
                FigureType.Bishop -> Bishop(figure.color())
                FigureType.Queen -> Queen(figure.color())
                else -> Queen(figure.color())
            }
            return true
        }
        return false
    }

    fun placePieces(position: Position, figure: Figure) {
        board[position] = figure
    }

    fun getAllFigures(whiteTurn: Boolean): List<Figure> {
        val allFigures = mutableListOf<Figure>()
        for (entry in board.entries) {
            if (entry.value.color() == Color.WHITE == whiteTurn) {
                allFigures.add(entry.value)
            }
        }
        return allFigures
    }

    fun findKing(whiteTurn: Boolean): Position? {
        for (entry in board.entries) {
            if (entry.value is King && (entry.value as King).color() == Color.WHITE == whiteTurn) {
                return entry.key
            }
        }
        return null
    }

    fun getAllPositions(): List<Position> {
        val allPositions = mutableListOf<Position>()
        for (entry in board.entries) {
            allPositions.add(entry.key)
        }
        return allPositions
    }

}