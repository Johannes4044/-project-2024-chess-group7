package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.*


class Game {
    var board: ChessBoard = ChessBoard.fullBoard()
    var currentPlayerIsWhite: Boolean = true
    val moves = mutableListOf<Move>()
    var totalMoves = 0

    // Für Undo und Stellungswiederholung
    val boardHistory = mutableListOf<String>()

    fun makeMove(from: Position, to: Position, promotionFigure: FigureType? = null): Boolean {
        val figure = board.getFigureAt(from) ?: return false
        val move = Move(from, to, board.copy(), currentPlayerIsWhite, totalMoves)
        if (move.isValid()) {
            // Board-Status für Undo und Wiederholung speichern
            boardHistory.add(FEN(board.toString()).toFenString())
            totalMoves += 1
            moves.add(move)
            if (move.isCapture() || figure is Pawn) {
                totalMoves = 0
            }
            board.move(from, to)
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
                        val move = Move(from, to, board.copy(), currentPlayerIsWhite, totalMoves)
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

    fun whiteCheck(): Boolean {
        val (_, blackMoves) = getAllMoves(board)
        val whiteKingPos = board.findKing(true) ?: return false
        return blackMoves.any { it.to == whiteKingPos }
    }

    fun blackCheck(): Boolean {
        val (whiteMoves, _) = getAllMoves(board)
        val blackKingPos = board.findKing(false) ?: return false
        return whiteMoves.any { it.to == blackKingPos }
    }

    fun isGameOver(): Boolean {
        val currentIsWhite = currentPlayerIsWhite
        val (whiteMoves, blackMoves) = getAllMoves(board)
        val playerMoves = if (currentIsWhite) whiteMoves else blackMoves
        val hasMoves = playerMoves.isNotEmpty()
        if (!hasMoves) return true
        if (totalMoves >= 50) return true
        if (isThreefoldRepetition()) return true
        return false
    }

    fun whiteKingsideCastling(game: Game): Boolean {
        val kingPos = board.findKing(true)!!
        val rookPos = Position(Column.H, Row.ONE)
        if (game.board.getFigureAt(kingPos) is King && game.board.getFigureAt(rookPos) is Rook &&
            game.board.isSpaceFree(game, Position(Column.F, Row.ONE), true) &&
            game.board.isSpaceFree(game, Position(Column.G, Row.ONE), true)) {
            board.placePieces(Position(Column.G, Row.ONE), King(Color.WHITE))
            board.placePieces(Position(Column.F, Row.ONE), Rook(Color.WHITE))
            board.removePiece(kingPos)
            board.removePiece(rookPos)
            return true
        }
        return false
    }

    fun blackKingsideCastling(game: Game): Boolean {
        val kingPos = Position(Column.E, Row.EIGHT)
        val rookPos = Position(Column.H, Row.EIGHT)
        if (game.board.getFigureAt(kingPos) is King && game.board.getFigureAt(rookPos) is Rook &&
            game.board.isSpaceFree(game, Position(Column.F, Row.EIGHT), false) &&
            game.board.isSpaceFree(game, Position(Column.G, Row.EIGHT), false)) {
            board.placePieces(Position(Column.G, Row.EIGHT), King(Color.BLACK))
            board.placePieces(Position(Column.F, Row.EIGHT), Rook(Color.BLACK))
            board.removePiece(kingPos)
            board.removePiece(rookPos)
            return true
        }
        return false
    }

    // Undo-Funktion: Setzt das Board auf den vorherigen Stand zurück
    fun undoMove() {
        if (moves.isNotEmpty()) {
            val lastMove = moves.removeAt(moves.size - 1)
            board = lastMove.board
            currentPlayerIsWhite = lastMove.playerBefore
            totalMoves = lastMove.totalMovesBefore
        }
    }

    // Dreifache Stellungswiederholung prüfen
    fun isThreefoldRepetition(): Boolean {
        val currentFEN = FEN(board.toString()).toFenString()
        return boardHistory.count { it == currentFEN } >= 2 // 2x in history + aktuelle = 3x
    }

    // Gibt den aktuellen Spielstatus als String zurück
    fun gameState(): String {
        val inCheck = if (currentPlayerIsWhite) whiteCheck() else blackCheck()
        val (whiteMoves, blackMoves) = getAllMoves(board)
        val hasMoves = if (currentPlayerIsWhite) whiteMoves.isNotEmpty() else blackMoves.isNotEmpty()
        return when {
            !hasMoves && inCheck -> "Schachmatt"
            !hasMoves && !inCheck -> "Patt"
            totalMoves >= 50 -> "Remis (50-Züge-Regel)"
            isThreefoldRepetition() -> "Remis (dreifache Stellungswiederholung)"
            else -> "Läuft"
        }
    }
}