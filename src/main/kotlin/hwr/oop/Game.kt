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
    val moves = mutableListOf<Move>()
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
        val figure = board.getFigureAt(from) ?: return false
        val move = Move(from, to, board)

        if (move.isValid()) {
            totalMoves += 1
            moves.add(move) // Add the move to the list of moves
            if (move.isCapture() || figure is Pawn) {
                totalMoves = 0 // Reset total moves if a pawn moves or a piece is captured
            }

            if (board.getFigureAt(to) is Pawn &&
                ((to.row == Row.EIGHT && figure.color() == Color.WHITE) || (to.row == Row.ONE && figure.color() == Color.BLACK))
            ) {
                board.promoteFigure(to, promotionFigure)
            }
            currentPlayerIsWhite = !currentPlayerIsWhite
            return true
        } else {
            throw IllegalStateException("Invalid move from $from to $to")
        }
    }

    /**
     * Retrieves all possible moves for both white and black pieces on the given board.
     *
     * @param board The chessboard to analyze.
     * @return A pair of lists: (whiteMoves, blackMoves).
     */

    fun getAllMoves(board: ChessBoard): Pair<List<Move>, List<Move>> {
        val whiteMoves = mutableListOf<Move>()
        val blackMoves = mutableListOf<Move>()
        val columns = Column.values()
        val rows = Row.values()

        for (column in columns) {
            for (row in rows) {
                val from = Position(column, row)
                val figure = board.getFigureAt(from)
                if (figure != null) {
                    val targets = figure.availableTargets(from, board)
                    for (to in targets) {
                        val move = Move(from, to, board)
                        if (figure.color() == Color.WHITE && figure !is King) {
                            whiteMoves.add(move)
                        }
                        if (figure.color() == Color.BLACK && figure !is King) {
                            blackMoves.add(move)
                        }
                    }
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
        val whiteKingPos = board.findKing(true) ?: return false
        return blackMoves.any { it.to == whiteKingPos }
    }

    /**
     * Checks if the black king is in check.
     *
     * @return True if the black king is in check, false otherwise.
     */

    fun blackCheck(): Boolean {
        val (whiteMoves, _) = getAllMoves(board)
        val blackKingPos = board.findKing(false) ?: return false
        return whiteMoves.any { it.to == blackKingPos }
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
        if (isWhiteCastling && blackMoves.any { it.to == position }) return false
        if (!isWhiteCastling && whiteMoves.any { it.to == position }) return false
        return true
    }

    /**
     * Performs kingside castling for white if possible.
     *
     * @param game The current game instance.
     * @return True if castling was performed, false otherwise.
     */

    fun whiteKingsideCastling(game: Game): Boolean {
        val kingPos = Position(Column.E, Row.ONE)
        val rookPos = Position(Column.H, Row.ONE)
        if (game.board.getFigureAt(kingPos) is King && game.board.getFigureAt(rookPos) is Rook &&
            game.isSpaceFree(game, Position(Column.F, Row.ONE), true) &&
            game.isSpaceFree(game, Position(Column.G, Row.ONE), true)) {
            game.makeMove(kingPos, Position(Column.G, Row.ONE))
            game.makeMove(rookPos, Position(Column.F, Row.ONE))
            return true
        }
        return false
    }
    /**
     * Performs kingside castling for black if possible.
     *
     * @param game The current game instance.
     * @return True if castling was performed, false otherwise.
     */
    fun blackKingsideCastling(game: Game): Boolean{
        val kingPos = Position(Column.E, Row.EIGHT)
        val rookPos = Position(Column.H, Row.EIGHT)
        if (game.board.getFigureAt(kingPos) is King && game.board.getFigureAt(rookPos) is Rook &&
            game.isSpaceFree(game, Position(Column.F, Row.EIGHT), false) &&
            game.isSpaceFree(game, Position(Column.G, Row.EIGHT), false)) {
            game.makeMove(kingPos, Position(Column.G, Row.EIGHT))
            game.makeMove(rookPos, Position(Column.F, Row.EIGHT))
            return true
        }
        return false
    }
}