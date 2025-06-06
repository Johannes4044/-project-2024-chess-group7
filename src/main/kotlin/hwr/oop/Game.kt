package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.King
import hwr.oop.figures.Pawn
import hwr.oop.figures.Rook

/**
 * Represents a chess game, managing the board state, player turns, moves, and game logic.
 */
class Game {
    var board: ChessBoard = ChessBoard.fullBoard()
    var currentPlayerIsWhite: Boolean = true
    val moves = mutableListOf<Triple<Figure, Position, Position>>()
    var totalMoves = 0

    /**
     * Attempts to make a move from one position to another, handling promotion if needed.
     *
     * @param from The starting position.
     * @param to The target position.
     * @param promotionFigure The figure type to promote to, if applicable.
     * @return True if the move was successful, false otherwise.
     */
    fun makeMove(from: Position, to: Position, promotionFigure: FigureType? = null): Boolean {
        val figure = board.getFigureAt(from)?: return false
        val move = Move(from, to, board)

        if (move.isValid()) {
            totalMoves += 1
            move.execute()
            if (move.isCapture() || figure is Pawn) {
                totalMoves = 0 // Reset total moves if a pawn moves or a piece is captured
            }

            if (board.getFigureAt(to) is Pawn &&
                ((to.row == Row.EIGHT && figure.color == Color.WHITE) || (to.row == Row.ONE && figure.color == Color.BLACK))) {
                board.promoteFigure(to, promotionFigure)
            }
            currentPlayerIsWhite = !currentPlayerIsWhite
            return true
        }else {
            return false
        }
    }

    /**
     * Retrieves all possible moves for both white and black pieces on the given board.
     *
     * @param board The chessboard to analyze.
     * @return A pair of lists: (whiteMoves, blackMoves).
     */

    fun getAllMoves(board: ChessBoard): Pair<List<Position>, List<Position>> {
        val whiteMoves = mutableListOf<Position>() //enthält alle weißen möglichen Züge
        val blackMoves = mutableListOf<Position>() //enthält alle schwarzen möglichen Züge
        val col = Column.values()
        val row = Row.values()

        for (column in col) {
            for (row in row) {
                val from = Position.valueOf("${column}${row}")
                val figure = board.getFigureAt(from)

                if (figure != King(Color.WHITE) && figure != null && figure.symbol()[0].isLowerCase()) {
                    val moves = figure.availableTargets(from, board)
                    whiteMoves.addAll(moves)    // Add all moves of a white piece to the list
                }
                if (figure != King(Color.BLACK) && figure != null && figure.symbol()[0].isUpperCase()) {
                    val moves = figure.availableTargets(from, board)
                    blackMoves.addAll(moves)    // Add all moves of a black piece to the list
                }
            }
        }
        return Pair(whiteMoves, blackMoves)
    }

    /**
     * Checks if the white king is in check.
     *
     * @return True if the white king is in check, false otherwise.
     */
    fun whiteCheck(): Boolean {
        val (_, blackMoves) = getAllMoves(board)
        val whiteKing = board.findKing(true)
        if (whiteKing == null) return false
        return blackMoves.contains(whiteKing)
    }

    /**
     * Checks if the black king is in check.
     *
     * @return True if the black king is in check, false otherwise.
     */
    fun blackCheck(): Boolean {
        val (whiteMoves, _) = getAllMoves(board)
        val blackKing = board.findKing(false)
        if (blackKing == null) return false
        return whiteMoves.contains(blackKing)
    }

    /**
     * Determines if the game is over due to checkmate, stalemate, or the 50-move rule.
     *
     * @return True if the game is over, false otherwise.
     */
    fun isGameOver(): Boolean {
        val currentIsWhite = currentPlayerIsWhite
        val (whiteMoves, blackMoves) = getAllMoves(board)

        val inCheck = if (currentIsWhite) whiteCheck() else blackCheck()
        val playerMoves = if (currentIsWhite) whiteMoves else blackMoves
        val hasMoves = playerMoves.isNotEmpty()

        if (!hasMoves) {
            return if (inCheck) {
                true
            } else {
                true
            }
        }

        if (totalMoves >= 50) {
            return true // 50-move rule
        }
        return false
    }

    /**
     * Checks if a given position is free from attack for castling.
     *
     * @param game The current game instance.
     * @param position The position to check.
     * @param isWhiteCastling True if checking for white, false for black.
     * @return True if the space is free, false otherwise.
     */
    fun isSpaceFree(game: Game, position: Position, isWhiteCastling: Boolean): Boolean {
        val (whiteMoves, blackMoves) = game.getAllMoves(board)
        if (whiteMoves.contains(position) && isWhiteCastling) return false
        if (blackMoves.contains(position) && !isWhiteCastling) return false
        return true
    }

    /**
     * Performs kingside castling for white if possible.
     *
     * @param game The current game instance.
     * @return True if castling was performed, false otherwise.
     */
    fun castleKingSide(game: Game):Boolean{
        val kingFirstMove = true
        val rookFirstMove = true
        val rookW = Rook(Color.WHITE)
        val kingW = King(Color.WHITE)

        if(kingFirstMove && rookFirstMove){
            val kingPosition = Position.E1
            val kingTo = Position.B1
            val rookPosition = Position.A1
            val rookTo = Position.C1

            board.placePieces(rookTo, rookW)
            board.placePieces(kingTo, kingW)
        }
        return true
    }
}