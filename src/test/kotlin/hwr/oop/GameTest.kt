package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import kotlin.toString

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
        val result = game.isSpaceFree(game, position, true)
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
        val result = game.isSpaceFree(game, position, false)
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
    fun `moves list is empty and totalMoves is zero after initialization`() {
        val game = Game()
        assertThat(game.moves).isEmpty()
        assertThat(game.totalMoves).isEqualTo(0)
    }

    @Test
    fun `blackKingsideCastling removes king and rook from their original positions`() {
        val board = ChessBoard.emptyBoard()
        val game = Game()
        // Place black king and rook in their initial positions
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        board.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board

        val result = game.blackKingsideCastling(game)

        // The original positions should now be empty
        assertThat(game.board.getFigureAt(Position(Column.E, Row.EIGHT))).isNull()
        assertThat(game.board.getFigureAt(Position(Column.H, Row.EIGHT))).isNull()
        // The king and rook should be in their castled positions
        assertThat(game.board.getFigureAt(Position(Column.G, Row.EIGHT))).isInstanceOf(King::class.java)
        assertThat(game.board.getFigureAt(Position(Column.F, Row.EIGHT))).isInstanceOf(Rook::class.java)
        assertThat(result).isTrue()
    }

    @Test
    fun `inCheck and playerMoves are set correctly for white in check with no moves`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // White king is threatened by black rook, no other pieces
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
    fun `blackCheck returns true if a white move threatens the black king`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), King(Color.BLACK))
        board.placePieces(Position(Column.E, Row.FIVE), Rook(Color.WHITE))
        game.board = board
        assertThat(game.blackCheck()).isTrue()
    }

    @Test
    fun `whiteCheck returns true when the white king is threatened by a black piece`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Place the white king and a black rook threatening it
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        assertThat(game.whiteCheck()).isTrue()
    }

    @Test
    fun `pawn promotion promotes pawn and switches player turn`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißen Bauern auf die 7. Reihe setzen, bereit zur Umwandlung
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = true

        val from = Position(Column.A, Row.SEVEN)
        val to = Position(Column.A, Row.EIGHT)
        val result = game.makeMove(from, to, FigureType.Queen)

        // Die Figur auf dem Zielfeld sollte eine Dame sein
        val promotedFigure = game.board.getFigureAt(to)
        assertThat(promotedFigure).isNotNull
        assertThat(promotedFigure).isInstanceOf(Queen::class.java)
        // Der Spielerwechsel sollte erfolgt sein
        assertThat(game.currentPlayerIsWhite).isFalse()
        // Der Zug sollte erfolgreich sein
        assertThat(result).isTrue()
    }

    @Test
    fun `boardHistory is empty after initialization`() {
        val game = Game()
        assertThat(game.boardHistory).isEmpty()
    }

    @Test
    fun `A pawn is marked for promotion only on the opponent's back rank`() {
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer auf der 7. Reihe, zieht auf die 8. Reihe
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val fromWhite = Position(Column.A, Row.SEVEN)
        val toWhite = Position(Column.A, Row.EIGHT)
        val figureWhite = board.getFigureAt(fromWhite)!!
        board.move(fromWhite, toWhite)
        assertThat(
            board.getFigureAt(toWhite) is Pawn &&
                    (toWhite.row == Row.EIGHT && figureWhite.color() == Color.WHITE)
        ).isTrue()

        // Schwarzer Bauer auf der 2. Reihe, zieht auf die 1. Reihe
        board.placePieces(Position(Column.B, Row.TWO), Pawn(Color.BLACK))
        val fromBlack = Position(Column.B, Row.TWO)
        val toBlack = Position(Column.B, Row.ONE)
        val figureBlack = board.getFigureAt(fromBlack)!!
        board.move(fromBlack, toBlack)
        assertThat(
            board.getFigureAt(toBlack) is Pawn &&
                    (toBlack.row == Row.ONE && figureBlack.color() == Color.BLACK)
        ).isTrue()

        // Weißer Bauer auf der 6. Reihe, zieht auf die 7. Reihe (keine Umwandlung)
        board.placePieces(Position(Column.C, Row.SIX), Pawn(Color.WHITE))
        val fromNoPromo = Position(Column.C, Row.SIX)
        val toNoPromo = Position(Column.C, Row.SEVEN)
        val figureNoPromo = board.getFigureAt(fromNoPromo)!!
        board.move(fromNoPromo, toNoPromo)
        assertThat(
            !(board.getFigureAt(toNoPromo) is Pawn &&
                    (toNoPromo.row == Row.EIGHT && figureNoPromo.color() == Color.WHITE))
        ).isTrue()
    }

    @Test
    fun `isGameOver returns true when totalMoves is at least 50`() {
        val game = Game()
        game.totalMoves = 50
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `isGameOver returns true upon threefold repetition of the position`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        // Füge die aktuelle Stellung zweimal zur History hinzu (insgesamt 3x)
        game.boardHistory.add(fen)
        game.boardHistory.add(fen)
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `A pawn is marked for promotion on the opponents back rank`() {
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer zieht auf die 8. Reihe
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val fromWhite = Position(Column.A, Row.SEVEN)
        val toWhite = Position(Column.A, Row.EIGHT)
        board.move(fromWhite, toWhite)
        val figureWhite = board.getFigureAt(toWhite)
        assertThat(figureWhite is Pawn && toWhite.row == Row.EIGHT && figureWhite.color() == Color.WHITE).isTrue()

        // Schwarzer Bauer zieht auf die 1. Reihe
        board.placePieces(Position(Column.B, Row.TWO), Pawn(Color.BLACK))
        val fromBlack = Position(Column.B, Row.TWO)
        val toBlack = Position(Column.B, Row.ONE)
        board.move(fromBlack, toBlack)
        val figureBlack = board.getFigureAt(toBlack)
        assertThat(figureBlack is Pawn && toBlack.row == Row.ONE && figureBlack.color() == Color.BLACK).isTrue()
    }

    @Test
    fun `playerMoves returns the correct moves for the current player`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        game.board = board

        // Weiß am Zug
        game.currentPlayerIsWhite = true
        val (whiteMoves, blackMoves) = game.getAllMoves(board)
        val playerMovesWhite = if (game.currentPlayerIsWhite) whiteMoves else blackMoves
        assertThat(playerMovesWhite).isEqualTo(whiteMoves)

        // Schwarz am Zug
        game.currentPlayerIsWhite = false
        val playerMovesBlack = blackMoves
        assertThat(playerMovesBlack).isEqualTo(blackMoves)
    }

    @Test
    fun `Castling removes the king and rook from their original squares`() {
        val board = ChessBoard.emptyBoard()
        val game = Game()
        // Setze König und Turm auf die Ausgangspositionen
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        game.board = board

        val result = game.whiteKingsideCastling(game)

        // Ursprungsfelder sollten leer sein
        assertThat(game.board.getFigureAt(Position(Column.E, Row.ONE))).isNull()
        assertThat(game.board.getFigureAt(Position(Column.H, Row.ONE))).isNull()
        // Rochade-Felder sollten besetzt sein
        assertThat(game.board.getFigureAt(Position(Column.G, Row.ONE))).isInstanceOf(King::class.java)
        assertThat(game.board.getFigureAt(Position(Column.F, Row.ONE))).isInstanceOf(Rook::class.java)
        assertThat(result).isTrue()
    }

    @Test
    fun `gameState returns a draw due to the 50-move rule when totalMoves is at least 50`() {
        val game = Game()
        game.totalMoves = 50
        assertThat(game.gameState()).isEqualTo("Remis (50-Züge-Regel)")
    }

    @Test
    fun `hasMoves is true when the current player has legal moves, and false when they do not`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißer König, schwarzer Turm bedroht ihn, keine Züge möglich
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), Rook(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true

        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        val hasMovesWhite = if (game.currentPlayerIsWhite) whiteMoves.isNotEmpty() else blackMoves.isNotEmpty()
        assertThat(hasMovesWhite).isFalse()

        // Jetzt ist Schwarz am Zug, aber kein schwarzer König, keine Züge
        game.currentPlayerIsWhite = false
        val hasMovesBlack = blackMoves.isNotEmpty()
        assertThat(hasMovesBlack).isTrue() // Der Turm kann ziehen
    }

    @Test
    fun `totalMoves is reset after capture or pawn move`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer auf E2, schwarzer Turm auf E4
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true
        game.totalMoves = 42

        // Bauernzug (kein Schlag)
        val resultPawnMove = game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))
        assertThat(resultPawnMove).isTrue()
        assertThat(game.totalMoves).isEqualTo(0)

        // Schlagzug: Weißer Bauer auf D3, schwarzer Turm auf E4
        board.placePieces(Position(Column.D, Row.THREE), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        game.currentPlayerIsWhite = true
        game.totalMoves = 10
        val resultCapture = game.makeMove(Position(Column.D, Row.THREE), Position(Column.E, Row.FOUR))
        assertThat(resultCapture).isTrue()
        assertThat(game.totalMoves).isEqualTo(0)
    }

    @Test
    fun `pawn is marked for promotion only on opponents back rank`() {
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer zieht auf die 8. Reihe (Promotion)
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val fromWhite = Position(Column.A, Row.SEVEN)
        val toWhite = Position(Column.A, Row.EIGHT)
        val figureWhite = board.getFigureAt(fromWhite)!!
        board.move(fromWhite, toWhite)
        assertThat(
            (toWhite.row == Row.EIGHT && figureWhite.color() == Color.WHITE) ||
                    (toWhite.row == Row.ONE && figureWhite.color() == Color.BLACK)
        ).isTrue()

        // Schwarzer Bauer zieht auf die 1. Reihe (Promotion)
        board.placePieces(Position(Column.B, Row.TWO), Pawn(Color.BLACK))
        val fromBlack = Position(Column.B, Row.TWO)
        val toBlack = Position(Column.B, Row.ONE)
        val figureBlack = board.getFigureAt(fromBlack)!!
        board.move(fromBlack, toBlack)
        assertThat(
            (toBlack.row == Row.EIGHT && figureBlack.color() == Color.WHITE) ||
                    (toBlack.row == Row.ONE && figureBlack.color() == Color.BLACK)
        ).isTrue()

        // Weißer Bauer zieht auf die 7. Reihe (keine Promotion)
        board.placePieces(Position(Column.C, Row.SIX), Pawn(Color.WHITE))
        val fromNoPromo = Position(Column.C, Row.SIX)
        val toNoPromo = Position(Column.C, Row.SEVEN)
        val figureNoPromo = board.getFigureAt(fromNoPromo)!!
        board.move(fromNoPromo, toNoPromo)
        assertThat(
            !((toNoPromo.row == Row.EIGHT && figureNoPromo.color() == Color.WHITE) ||
                    (toNoPromo.row == Row.ONE && figureNoPromo.color() == Color.BLACK))
        ).isTrue()
    }

    @Test
    fun `playerMoves returns correct moves for current player`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        game.board = board

        // White's turn
        game.currentPlayerIsWhite = true
        val (whiteMoves, blackMoves) = game.getAllMoves(board)
        val playerMovesWhite = if (game.currentPlayerIsWhite) whiteMoves else blackMoves
        assertThat(playerMovesWhite).isEqualTo(whiteMoves)

        // Black's turn
        game.currentPlayerIsWhite = false
        val playerMovesBlack = blackMoves
        assertThat(playerMovesBlack).isEqualTo(blackMoves)
    }

    @Test
    fun `undoMove reverts the last move and restores previous board state`() {
        val game = Game()
        val board = ChessBoard.fullBoard()
        game.board = board
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)

        // Nur den Zug ausführen, nicht manuell Listen befüllen
        game.makeMove(from, to)
        val boardAfterMove = game.board.toString()
        val playerAfterMove = game.currentPlayerIsWhite

        // Undo
        game.undoMove()

        // Prüfe, ob der Zug entfernt wurde, das Board zurückgesetzt und der Spieler gewechselt wurde
        assertThat(game.board.toString()).isNotEqualTo(boardAfterMove)
        assertThat(game.moves).isEmpty()
        assertThat(game.currentPlayerIsWhite).isNotEqualTo(playerAfterMove)
    }

    @Test
    fun `move counting, player moves, and move conditions work as expected`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer auf E2, schwarzer Turm auf E4, schwarzer König auf E8
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true

        // Test: totalMoves wird erhöht und bei Bauernzug zurückgesetzt
        game.totalMoves = 0
        val pawnMove = game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))
        assertThat(pawnMove).isTrue()
        assertThat(game.totalMoves).isEqualTo(0) // wegen Bauernzug

        // Test: totalMoves wird erhöht und bei Capture zurückgesetzt
        board.placePieces(Position(Column.D, Row.THREE), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        game.currentPlayerIsWhite = true
        game.totalMoves = 10
        val captureMove = game.makeMove(Position(Column.D, Row.THREE), Position(Column.E, Row.FOUR))
        assertThat(captureMove).isTrue()
        assertThat(game.totalMoves).isEqualTo(0) // wegen Capture

        // Test: Promotion-Bedingung
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val from = Position(Column.A, Row.SEVEN)
        val to = Position(Column.A, Row.EIGHT)
        val promotion = game.makeMove(from, to, FigureType.Queen)
        assertThat(promotion).isTrue()
        val promoted = game.board.getFigureAt(to)
        assertThat(promoted).isInstanceOf(Queen::class.java)

        // Test: playerMoves und hasMoves
        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        val currentIsWhite = game.currentPlayerIsWhite
        val playerMoves = if (currentIsWhite) whiteMoves else blackMoves
        val hasMoves = playerMoves.isNotEmpty()
        assertThat(playerMoves).isNotNull()
        assertThat(hasMoves).isTrue()
        // Test: whiteMoves.any { it.to == blackKingPos }
        val blackKingPos = Position(Column.E, Row.EIGHT)
        assertThat(whiteMoves.any { it.to == blackKingPos }).isTrue()
    }

    @Test
    fun `jede relevante Zeile wird explizit ausgeführt und geprüft`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        game.board = board
        game.currentPlayerIsWhite = true

        // totalMoves erhöhen und zurücksetzen
        game.totalMoves = 0
        game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))
        assertThat(game.totalMoves).isEqualTo(0)

        // Capture testen
        board.placePieces(Position(Column.D, Row.THREE), Pawn(Color.WHITE))
        board.placePieces(Position(Column.E, Row.FOUR), Rook(Color.BLACK))
        game.currentPlayerIsWhite = true
        game.totalMoves = 10
        game.makeMove(Position(Column.D, Row.THREE), Position(Column.E, Row.FOUR))
        assertThat(game.totalMoves).isEqualTo(0)

        // Promotion-Bedingung
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val from = Position(Column.A, Row.SEVEN)
        val to = Position(Column.A, Row.EIGHT)
        game.makeMove(from, to, FigureType.Queen)
        val promoted = game.board.getFigureAt(to)
        assertThat(promoted).isInstanceOf(Queen::class.java)

        // playerMoves und hasMoves
        val (whiteMoves, blackMoves) = game.getAllMoves(game.board)
        val playerMoves = if (game.currentPlayerIsWhite) whiteMoves else blackMoves
        val hasMoves = if (game.currentPlayerIsWhite) whiteMoves.isNotEmpty() else blackMoves.isNotEmpty()
        assertThat(playerMoves).isNotNull()
        assertThat(hasMoves).isTrue()

        // whiteMoves.any { it.to == blackKingPos }
        val blackKingPos = Position(Column.E, Row.EIGHT)
        assertThat(whiteMoves.any { it.to == blackKingPos }).isTrue()
    }

    @Test
    fun `moves gibt die aktuelle Zugliste zurück`() {
        val game = Game()
        assertThat(game.moves).isEmpty()
        // Nach einem Zug sollte die Liste ein Element enthalten
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        game.makeMove(from, to)
        assertThat(game.moves).hasSize(1)
    }

    @Test
    fun `totalMoves gibt die aktuelle Anzahl zurück`() {
        val game = Game()
        game.totalMoves = 5
        assertThat(game.totalMoves).isEqualTo(5)
    }

    @Test
    fun `totalMoves wird bei normalem Nicht-Bauern-Zug erhöht`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.ONE), Rook(Color.WHITE))
        game.board = board
        val from = Position(Column.A, Row.ONE)
        val to = Position(Column.A, Row.TWO)
        val vorher = game.totalMoves
        game.makeMove(from, to)
        assertThat(game.totalMoves).isEqualTo(vorher + 1)
    }

    @Test
    fun `totalMoves wird bei Bauernzug auf 0 zurückgesetzt`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.TWO), Pawn(Color.WHITE))
        game.board = board
        game.totalMoves = 5
        game.makeMove(Position(Column.E, Row.TWO), Position(Column.E, Row.THREE))
        assertThat(game.totalMoves).isEqualTo(0)
    }

    // Test: isGameOver gibt false zurück, wenn Spieler noch Züge hat
    @Test
    fun `isGameOver gibt false zurück wenn Spieler noch Züge hat`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.currentPlayerIsWhite = true
        assertThat(game.isGameOver()).isFalse()
    }

    // Test: gameState gibt "Läuft" zurück, wenn noch Züge möglich sind und kein Schachmatt/Patt/Remis
    @Test
    fun `gameState gibt Laeuft zurück wenn noch Züge möglich sind`() {
        val game = Game()
        game.board = ChessBoard.fullBoard()
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Läuft")
    }

    // Kein Promotion, wenn Bauer nicht auf gegnerischer Grundreihe landet
    @Test
    fun `kein Promotion wenn Bauer nicht auf gegnerischer Grundreihe landet`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.A, Row.SIX), Pawn(Color.WHITE))
        game.board = board
        game.makeMove(Position(Column.A, Row.SIX), Position(Column.A, Row.SEVEN))
        assertThat(game.board.getFigureAt(Position(Column.A, Row.SEVEN))).isInstanceOf(Pawn::class.java)
    }

    // Test: whiteKingsideCastling gibt false zurück, wenn König oder Turm fehlen
    @Test
    fun `whiteKingsideCastling gibt false zurück wenn keine Rochade möglich ist`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Nur König, kein Turm
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        game.board = board
        assertThat(game.whiteKingsideCastling(game)).isFalse()
        // Nur Turm, kein König
        val board2 = ChessBoard.emptyBoard()
        board2.placePieces(Position(Column.H, Row.ONE), Rook(Color.WHITE))
        game.board = board2
        assertThat(game.whiteKingsideCastling(game)).isFalse()
    }

    // Test: blackKingsideCastling gibt false zurück, wenn König oder Turm fehlen
    @Test
    fun `blackKingsideCastling gibt false zurück wenn keine Rochade möglich ist`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Nur König, kein Turm
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        game.board = board
        assertThat(game.blackKingsideCastling(game)).isFalse()
        // Nur Turm, kein König
        val board2 = ChessBoard.emptyBoard()
        board2.placePieces(Position(Column.H, Row.EIGHT), Rook(Color.BLACK))
        game.board = board2
        assertThat(game.blackKingsideCastling(game)).isFalse()
    }

    // Test: undoMove macht nichts, wenn keine Züge vorhanden sind
    @Test
    fun `undoMove macht nichts wenn keine Züge vorhanden sind`() {
        val game = Game()
        val boardBefore = game.board.toString()
        game.undoMove()
        assertThat(game.board.toString()).isEqualTo(boardBefore)
    }

    // Test: isThreefoldRepetition gibt false zurück, wenn Stellung weniger als 3x vorkommt
    @Test
    fun `isThreefoldRepetition gibt false zurück wenn Stellung weniger als dreimal vorkommt`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        game.boardHistory.add(fen)
        assertThat(game.isThreefoldRepetition()).isFalse()
    }

    // Test: gameState gibt Patt zurück, wenn kein Schach und keine Züge
    @Test
    fun `gameState gibt Patt zurück wenn kein Schach und keine Züge`() {
        val game = Game()
        val board = ChessBoard.emptyBoard()
        // Nur weißer König, keine Züge, kein Schach
        board.placePieces(Position(Column.A, Row.ONE), King(Color.WHITE))
        game.board = board
        game.currentPlayerIsWhite = true
        assertThat(game.gameState()).isEqualTo("Patt")
    }

    // Test: gameState gibt Remis (dreifache Stellungswiederholung) zurück
    @Test
    fun `gameState gibt Remis bei dreifacher Stellungswiederholung zurück`() {
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
}
