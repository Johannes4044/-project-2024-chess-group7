package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
        board.placePieces(Position(Column.E, Row.ONE), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }
    @Test
    fun `valid move changes player turn`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to, null)
        assertThat(game.isGameOver()).isFalse()
    }
    @Test
    fun `currentPlayerIsWhite changes after valid move`() {
        val game = Game()
        val startValue = game.currentPlayerIsWhite
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        assertThat(game.currentPlayerIsWhite).isNotEqualTo(startValue)
    }

    @Test
    fun `whiteCheck returns false if there is no white king on the board`() {
        val game = Game()
        game.board = ChessBoard.emptyBoard()
        assertThat(game.whiteCheck()).isFalse()
    }

    @Test
    fun `players alternate turns`() {
        val game = Game()
        val fromWhite = Position(Column.E, Row.TWO)
        val toWhite = Position(Column.E, Row.FOUR)
        val fromBlack = Position(Column.D, Row.SEVEN)
        val toBlack = Position(Column.D, Row.FIVE)

        game.makeMove(fromWhite, toWhite)
        assertThat(game.isGameOver()).isFalse()

        game.makeMove(fromBlack, toBlack)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `valid move captures opponent's figure`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        val fromBlack = Position(Column.D, Row.SEVEN)
        val toBlack = Position(Column.D, Row.FIVE)
        game.makeMove(fromBlack, toBlack)
        val fromWhiteCapture = Position(Column.E, Row.FOUR)
        val toWhiteCapture = Position(Column.D, Row.FIVE)
        game.makeMove(fromWhiteCapture, toWhiteCapture)
        assertThat(game.isGameOver()).isFalse()
    }

    @Test
    fun `game ends in stalemate`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        board.move(Position(Column.H, Row.TWO), Position(Column.H, Row.THREE))
        board.move(Position(Column.F, Row.TWO), Position(Column.F, Row.THREE))
        board.move(Position(Column.G, Row.TWO), Position(Column.G, Row.THREE))
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

        // Platziere nur Türme und Könige für einfache Züge
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))

        game.board = board

        // Führe 50 Züge aus
        repeat(25) {
            game.makeMove(Position(Column.H, Row.ONE), Position(Column.H, Row.TWO))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.H, Row.TWO), Position(Column.H, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `game keeps going after over 50 moves if a pawn moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()

        // Platziere Figuren
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        board.placePieces(Position(Column.A, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))

        game.board = board

        // Mache 48 Züge
        repeat(24) {
            game.makeMove(Position(Column.A, Row.ONE), Position(Column.B, Row.ONE))
            game.makeMove(Position(Column.H, Row.EIGHT), Position(Column.H, Row.SEVEN))
            game.makeMove(Position(Column.B, Row.ONE), Position(Column.A, Row.ONE))
            game.makeMove(Position(Column.H, Row.SEVEN), Position(Column.H, Row.EIGHT))
        }

        // Führe Bauernzug aus
        game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))

        assertThat(game.totalMoves).isEqualTo(0)
        assertThat(game.isGameOver()).isFalse()
    }
    @Test
    fun `castleKingSide setzt König und Turm korrekt um`() {
        val board = ChessBoard.emptyBoard()
        val game = Game()
        val move = Move(Position(Column.E, Row.ONE), Position(Column.G, Row.ONE), board)
        move.castleKingSide(game)
        val king = board.getFigureAt(Position(Column.B, Row.ONE))
        val rook = board.getFigureAt(Position(Column.C, Row.ONE))
        assertThat(king).isInstanceOf(King::class.java)
        assertThat(rook).isInstanceOf(Rook::class.java)
    }

    @Test
    fun `isSpaceFree returns true when position is not attacked for white castling`() {
        val game = Game()
        val position = Position(Column.F, Row.ONE)
        // Keine schwarzen Züge auf F1
        assertThat(game.isSpaceFree(game, position, true)).isTrue()
    }


    @Test
    fun `isSpaceFree returns true when position is not attacked for black castling`() {
        val game = Game()
        val position = Position(Column.F, Row.EIGHT)
        // Keine weißen Züge auf F8
        assertThat(game.isSpaceFree(game, position, false)).isTrue()
    }

    @Test
    fun `initial game has empty moves list and totalMoves is zero`() {
        val game = Game()
        assertThat(game.moves).isEmpty()
        assertThat(game.totalMoves).isEqualTo(0)
    }


    @Test
    fun `inCheck, playerMoves und hasMoves werden korrekt gesetzt`() {
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
    fun `isSpaceFree gibt false zurück wenn Feld von Schwarz angegriffen wird beim weißen Rochieren`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.F, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        val position = Position(Column.F, Row.ONE)
        val result = game.isSpaceFree(game, position, true)
        assertThat(result).isFalse()
    }

    @Test
    fun `getHalfmoveClock, getFullmoveNumber und toFenString geben korrekte Werte zurück`() {
        val fenString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 5 12"
        val fen = FEN(fenString)

        assertThat(fen.getHalfmoveClock()).isEqualTo(5)
        assertThat(fen.getFullmoveNumber()).isEqualTo(12)
        assertThat(fen.toFenString()).isEqualTo(fenString)
    }

    @Test
    fun `moves Liste ist leer und totalMoves ist 0 nach Initialisierung`() {
        val moves = mutableListOf<Move>()
        var totalMoves = 0

        assertThat(moves).isEmpty()
        assertThat(totalMoves).isEqualTo(0)
    }

    @Test
    fun `inCheck und playerMoves werden korrekt gesetzt wenn weiß im Schach und keine Züge möglich`() {
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
    fun `isSpaceFree gibt false zurück wenn Feld von Weiß angegriffen wird beim schwarzen Rochieren`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.F, Row.ONE), Rook(Color.WHITE))
        game.board = board
        val position = Position(Column.F, Row.EIGHT)
        val result = game.isSpaceFree(game, position, false)
        assertThat(result).isFalse()
    }
}
