package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.assertThrows

class GameTest : AnnotationSpec() {
    @Test
    fun `whiteCheck returns true if black move threatens white king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `blackCheck returns false if there is no black king on the board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.blackCheck()).isFalse()
    }

    @Test
    fun `blackCheck returns true if white move threatens black king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.ONE), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }

    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        game.board = ChessBoard.fullBoard() // Standardaufstellung
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        val result = game.makeMove(from, to)
        assertThat(result).isTrue()
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `currentPlayerIsWhite switches after a valid move`() {
        val game = Game()
        game.board = ChessBoard.fullBoard() // Standardaufstellung
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        val vorher = game.currentPlayerIsWhite
        val result = game.makeMove(from, to)
        assertThat(result).isTrue()
        assertThat(game.currentPlayerIsWhite).isNotEqualTo(vorher)
    }

    @Test
    fun `whiteCheck returns false if there is no white king on the board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.whiteCheck()).isFalse()
    }

    @Test
    fun `game does not end in stalemate for initial position with only a few pawn moves`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        board.move(Position(Column.H, Row.TWO), Position(Column.H, Row.THREE))
        board.move(Position(Column.F, Row.TWO), Position(Column.F, Row.THREE))
        board.move(Position(Column.G, Row.TWO), Position(Column.G, Row.THREE))
        game.board = board
        game.currentPlayerIsWhite = true

        val isStalemate = game.isGameOver()
        assertThat(isStalemate).isFalse()
    }

    @Test
    fun `getAllMoves return all available moves back`() {
        val game = Game()
        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        assertThat(whiteMoves).isNotEmpty
        assertThat(blackMoves).isNotEmpty
    }

    @Test
    fun `white is checked`() {
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board = chessBoard
        chessBoard.placePieces(Position(Column.E, Row.TWO), King(Color.WHITE))
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Rook(Color.BLACK))
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `black is checked`(){
        val game = Game()
        val chessBoard = ChessBoard.emptyBoard()
        game.board  = chessBoard
        chessBoard.placePieces(Position(Column.E, Row.TWO), King(Color.BLACK))
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Rook(Color.WHITE))
        assertThat(game.blackCheck()).isTrue()
    }

    @Test
    fun `getAllFigures return all white figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val whiteFigures = chessBoard.getAllFigures(true) as List<*>
        assertThat(whiteFigures).hasSize(16)
        assertThat(whiteFigures.all { (it as Figure).color() == Color.WHITE }).isTrue()    }

    @Test
    fun `getAllFigures return all black figures back`() {
        val chessBoard = ChessBoard.fullBoard()
        val blackFigures = chessBoard.getAllFigures(false) as List<*>
        assertThat(blackFigures).hasSize(16)
        assertThat(blackFigures.all { (it as Figure).color() == Color.BLACK }).isTrue()}

    @Test
    fun `getAllPositions return all occupied positions back`() {
        val chessBoard = ChessBoard.fullBoard()
        val positions = chessBoard.getAllPositions() as List<*>
        assertThat(positions).hasSize(32)
        assertThat(positions.all { it is Position }).isTrue()
    }

    @Test
    fun `move from empty position returns false`() {
        val game = Game()
        val from = Position(Column.A, Row.THREE)
        val to = Position(Column.A, Row.FOUR)
        val result = game.makeMove(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `game is over when no king on board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `cannot move if game is over`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.isGameOver()).isTrue()
        val result = game.makeMove(Position(Column.A, Row.ONE), Position(Column.A, Row.TWO))
        assertThat(result).isFalse()
    }

    @Test
    fun `game ends after 50 moves without pawn move or capture`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Place only rooks and kings for simple moves
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))

        game.board = board

        // Perform 50 half-moves (25 moves per player)
        repeat(25) {
            game.makeMove(Position(Column.H, Row.ONE), Position(Column.H, Row.TWO))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.H, Row.TWO), Position(Column.H, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        // After 50 half-moves without pawn move or capture, the game should be over (draw)
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `from returns the position for a valid column and row`() {
        val pos = Position.from('a', 1)
        assertThat(pos).isEqualTo(Position(Column.A, Row.ONE))
    }

    @Test
    fun `from returns null for an invalid column`() {
        val pos = Position.from('z', 1)
        assertThat(pos).isNull()
    }

    @Test
    fun `from returns null for an invalid row`() {
        val pos = Position.from('a', 9)
        assertThat(pos).isNull()
    }

    @Test
    fun `from returns null for an invalid column and row`() {
        val pos = Position.from('z', 9)
        assertThat(pos).isNull()
    }

    @Test
    fun `isSpaceFree returns true when position is not attacked for white castling`() {
        val game = Game()
        val position = Position(Column.F, Row.ONE)
        // Keine schwarzen Züge auf F1
        assertThat(game.board.isSpaceFree(game, position, true)).isTrue()
    }

    @Test
    fun `isSpaceFree returns true when position is not attacked for black castling`() {
        val game = Game()
        val position = Position(Column.F, Row.EIGHT)
        // Keine weißen Züge auf F8
        assertThat(game.board.isSpaceFree(game, position, true)).isTrue()
    }

    @Test
    fun `initial game has empty moves list and totalMoves is zero`() {
        val game = Game()
        assertThat(game.moves).isEmpty()
        assertThat(game.totalMoves).isEqualTo(0)
    }

    @Test
    fun `inCheck, playerMoves, and hasMoves are set correctly`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißer König wird von schwarzem Turm bedroht
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true

        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        val inCheck = if (game.currentPlayerIsWhite) game.whiteCheck() else game.blackCheck()
        val playerMoves = if (game.currentPlayerIsWhite) whiteMoves else blackMoves
        val hasMoves = playerMoves.isNotEmpty()

        assertThat(inCheck).isTrue()
        assertThat(playerMoves).isEmpty()
        assertThat(hasMoves).isFalse()
    }

    @Test
    fun `isSpaceFree returns false if the square is attacked by Black during white castling`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.F, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        val position = Position(Column.F, Row.ONE)
        val result = game.board.isSpaceFree(game, position, true)
        assertThat(result).isFalse()
    }

    @Test
    fun `getHalfMoveClock, getFullMoveNumber, and toFenString return correct values`() {
        val fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 5 12"
        val fen = FEN(fenString)

        assertThat(fen.getHalfMoveClock()).isEqualTo(5)
        assertThat(fen.getFullMoveNumber()).isEqualTo(12)
        assertThat(fen.toFenString()).isEqualTo(fenString)
    }

    @Test
    fun `inCheck and playerMoves are set correctly when white is in check and has no legal moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißer König wird von schwarzem Turm bedroht, keine anderen Figuren
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true

        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        val inCheck = if (game.currentPlayerIsWhite) game.whiteCheck() else game.blackCheck()
        val playerMoves = if (game.currentPlayerIsWhite) whiteMoves else blackMoves

        assertThat(inCheck).isTrue()
        assertThat(playerMoves).isEmpty()
    }

    @Test
    fun `isSpaceFree returns false if the square is attacked by White during black castling`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.F, Row.ONE), Rook(Color.WHITE))
        game.board = board
        val position = Position(Column.F, Row.EIGHT)
        val result = game.board.isSpaceFree(game, position, false)
        assertThat(result).isFalse()
    }

    @Test
    fun `gameState returns correct status for various game situations`() {
        val game = Game()
        // Checkmate situation for White
        val boardCheckmate = ChessBoard.emptyBoard()
        boardCheckmate.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        boardCheckmate.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = boardCheckmate
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Schachmatt")

        // Stalemate situation (no check, no moves)
        val boardStalemate = ChessBoard.emptyBoard()
        boardStalemate.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        boardStalemate.placePieces(Position(Column.H, Row.EIGHT), King(Color.BLACK))
        game.board = boardStalemate
        game.currentPlayerIsWhite = true
        game.totalMoves = 50
        assertThat(game.gameState()).isEqualTo("Patt")

        // Ongoing
        game.totalMoves = 0
        game.board = ChessBoard.fullBoard()
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Läuft")
    }

    @Test
    fun `moves returns the current move list`() {
        val game = Game()
        assertThat(game.moves).isEmpty()
        // After a move, the list should contain one element
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        assertThat(game.moves).hasSize(1)
    }

    @Test
    fun `totalMoves returns the current count`() {
        val game = Game()
        game.totalMoves = 5
        assertThat(game.totalMoves).isEqualTo(5)
    }

    @Test
    fun `totalMoves increases for normal non-pawn move`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.ONE), Rook(Color.WHITE))
        game.board = board
        val from = Position(Column.A, Row.ONE)
        val to = Position(Column.A, Row.TWO)
        val before = game.totalMoves
        game.makeMove(from, to)
        assertThat(game.totalMoves).isEqualTo(before + 1)
    }

    @Test
    fun `totalMoves resets to 0 after pawn move`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        game.board = board
        game.totalMoves = 5
        game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))
        assertThat(game.totalMoves).isEqualTo(0)
    }

    @Test
    fun `isGameOver returns false if player still has moves`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.currentPlayerIsWhite = true
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `gameState returns Running if moves are possible and no checkmate, stalemate or draw`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Läuft")
    }

    @Test
    fun `no promotion if pawn does not reach opponent's back rank`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.SIX), Pawn(Color.WHITE))
        game.board = board
        game.makeMove(Position(Column.A, Row.SIX), Position(Column.A, Row.SEVEN))
        assertThat(game.board.getFigureAt(Position(Column.A, Row.SEVEN))).isInstanceOf(Pawn::class.java)
    }

    @Test
    fun `blackKingsideCastling returns false if castling is not possible`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Only king, no rook
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        game.board = board
        assertThat(game.blackKingsideCastling(game)).isFalse()
        // Only rook, no king
        val board2 = ChessBoard.emptyBoard()
        board2.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board2
        assertThat(game.blackKingsideCastling(game)).isFalse()
    }

    @Test
    fun `undoMove does nothing if there are no moves`() {
        val game = Game()
        val boardBefore = game.board.toString()
        game.undoMove()
        assertThat(game.board.toString()).isEqualTo(boardBefore)
    }

    @Test
    fun `isThreefoldRepetition returns false if position occurs less than three times`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        game.boardHistory.add(fen)
        assertThat(game.isThreefoldRepetition()).isFalse()
    }

    @Test
    fun `gameState returns Stalemate if no check and no moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Only white king, no moves, no check
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Patt")
    }

    @Test
    fun `gameState returns Draw for threefold repetition`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        game.boardHistory.add(fen)
        game.boardHistory.add(fen)
        assertThat(game.gameState()).isEqualTo("Remis (dreifache Stellungswiederholung)")
    }

    @Test
    fun `white pawn reaches 8th row triggers promotion`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.SEVEN), Pawn(Color.WHITE))
        game.board = board
        val moved = game.makeMove(Position(Column.E, Row.SEVEN), Position(Column.E, Row.EIGHT), FigureType.Queen)
        assertThat(moved).isTrue()
        assertThat(game.board.getFigureAt(Position(Column.E, Row.EIGHT))).isInstanceOf(Queen::class.java)
    }

    @Test
    fun `black pawn reaches 1st row triggers promotion`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.BLACK))
        game.board = board
        val moved = game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.ONE), FigureType.Queen)
        assertThat(moved).isTrue()
        assertThat(game.board.getFigureAt(Position(Column.E, Row.ONE))).isInstanceOf(Queen::class.java)
    }

    @Test
    fun `black king is in check returns true`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }

    @Test
    fun `game is over when player has no moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.TWO), Queen(Color.WHITE))
        board.placePieces(Position(Column.D, Row.TWO), Queen(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = false
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `game state running when current player has legal moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        game.board = board
        assertThat(game.gameState()).isEqualTo("Läuft")
    }

    @Test
    fun `game state is checkmate when no moves and king in check`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.H, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.G, Row.SEVEN), Queen(Color.WHITE))
        board.placePieces(Position(Column.F, Row.SIX), King(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = false
        assertThat(game.gameState()).isEqualTo("Schachmatt")
    }

    @Test
    fun `game state is stalemate when no moves and king not in check`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.H, Row.ONE), King(Color.BLACK))
        board.placePieces(Position(Column.F, Row.TWO), King(Color.WHITE))
        board.placePieces(Position(Column.G, Row.THREE), Queen(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = false
        assertThat(game.gameState()).isEqualTo("Patt")
    }

    @Test
    fun `game is over after 50 moves without pawn move or capture`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board

        repeat(25) {
            game.makeMove(Position(Column.H, Row.ONE), Position(Column.H, Row.TWO))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.H, Row.TWO), Position(Column.H, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `game is over after threefold repetition`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        game.boardHistory.add(fen)
        game.boardHistory.add(fen)
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `white kingside castling moves king and rook to correct positions`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Setze König und Turm auf Ausgangsposition
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        game.board = board

        val result = game.whiteKingsideCastling(game)

        assertThat(result).isTrue()
        assertThat(game.board.getFigureAt(Position(Column.G, Row.ONE))).isInstanceOf(King::class.java)
        assertThat(game.board.getFigureAt(Position(Column.F, Row.ONE))).isInstanceOf(Rook::class.java)
        assertThat(game.board.getFigureAt(Position(Column.E, Row.ONE))).isNull()
        assertThat(game.board.getFigureAt(Position(Column.H, Row.ONE))).isNull()
    }

    @Test
    fun `black kingside castling moves king and rook to correct positions`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Setze König und Turm auf Ausgangsposition
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board

        val result = game.blackKingsideCastling(game)

        assertThat(result).isTrue()
        assertThat(game.board.getFigureAt(Position(Column.G, Row.EIGHT))).isInstanceOf(King::class.java)
        assertThat(game.board.getFigureAt(Position(Column.F, Row.EIGHT))).isInstanceOf(Rook::class.java)
        assertThat(game.board.getFigureAt(Position(Column.E, Row.EIGHT))).isNull()
        assertThat(game.board.getFigureAt(Position(Column.H, Row.EIGHT))).isNull()
    }

    @Test
    fun `gameState returns Draw after 50 moves without pawn move or capture`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board

        // Perform 50 half-moves (25 moves per player)
        repeat(25) {
            game.makeMove(Position(Column.H, Row.ONE), Position(Column.H, Row.TWO))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.H, Row.TWO), Position(Column.H, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        assertThat(game.gameState()).isEqualTo("Remis (50-Züge-Regel)")
    }


    @Test
    fun `findKing should return position of white king when it's white's turn`() {
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        val result = board.findKing(whiteTurn = true)
        assertThat(result).isEqualTo(Position(Column.E, Row.ONE))
    }

    @Test
    fun `undoMove stellt Brett und Spieler korrekt wieder her`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.board.placePieces(from, Pawn(Color.WHITE))
        val playerBefore = game.currentPlayerIsWhite
        val boardBefore = game.board.copy()

        game.makeMove(from, to)
        assertThat(game.board.getFigureAt(to)).isInstanceOf(Pawn::class.java)
        assertThat(game.currentPlayerIsWhite).isNotEqualTo(playerBefore)

        game.undoMove()
        assertThat(game.board.getFigureAt(from)).isInstanceOf(Pawn::class.java)
        assertThat(game.board.getFigureAt(to)).isNull()
        assertThat(game.currentPlayerIsWhite).isEqualTo(playerBefore)
        assertThat(game.board.getAllPositions()).containsExactlyInAnyOrderElementsOf(boardBefore.getAllPositions())
    }

    @Test
    fun `game ends in draw after 50 moves rule`() {
        val game = Game()
        game.totalMoves = 50
        assertThat(game.isGameOver()).isTrue()
        assertThat(game.gameState()).isEqualTo("Remis (50-Züge-Regel)")
    }

    @Test
    fun `whiteKingsideCastling throws if no white king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Kein weißer König auf dem Brett
        game.board = board
        assertThrows<NullPointerException> {
            game.whiteKingsideCastling(game)
        }
    }

    @Test
    fun `whiteKingsideCastling returns false if castling not possible`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        game.board = board
        assertFalse(game.whiteKingsideCastling(game))
    }
}