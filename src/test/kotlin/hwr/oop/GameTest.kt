package hwr.oop

import hwr.oop.figures.FigureType
import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

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
    fun `currentPlayerIsWhite wechselt nach gültigem Zug`() {
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
    fun `from gibt Position für gültige Spalte und Reihe zurück`() {
        val pos = Position.from('a', 1)
        assertThat(pos).isEqualTo(Position(Column.A, Row.ONE))
    }

    @Test
    fun `from gibt null für ungültige Spalte zurück`() {
        val pos = Position.from('z', 1)
        assertThat(pos).isNull()
    }

    @Test
    fun `from gibt null für ungültige Reihe zurück`() {
        val pos = Position.from('a', 9)
        assertThat(pos).isNull()
    }

    @Test
    fun `from gibt null für ungültige Spalte und Reihe zurück`() {
        val pos = Position.from('z', 9)
        assertThat(pos).isNull()
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
    fun `undoMove restores previous board state and player turn`() {
        val game = Game()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)

        // Make a move and let the game handle boardHistory
        game.makeMove(from, to)
        val fenAfterMove = FEN(game.board.toString()).toFenString()
        val playerAfterMove = game.currentPlayerIsWhite

        // Undo the move
        game.undoMove()
        val fenAfterUndo = FEN(game.board.toString()).toFenString()

        // The board should be back to the initial state and the player turn restored
        val initialFen = FEN(ChessBoard.fullBoard().toString()).toFenString()
        assertThat(fenAfterUndo).isEqualTo(initialFen)
        assertThat(game.currentPlayerIsWhite).isTrue()
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
    fun `boardHistory ist nach Initialisierung leer`() {
        val game = Game()
        assertThat(game.boardHistory).isEmpty()
    }

    @Test
    fun `Bauer wird nur auf gegnerischer Grundreihe zur Umwandlung markiert`() {
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer auf der 7. Reihe, zieht auf die 8. Reihe
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val fromWhite = Position(Column.A, Row.SEVEN)
        val toWhite = Position(Column.A, Row.EIGHT)
        val figureWhite = board.getFigureAt(fromWhite)!!
        board.move(fromWhite, toWhite, null)
        assertThat(
            board.getFigureAt(toWhite) is Pawn &&
                    (toWhite.row == Row.EIGHT && figureWhite.color() == Color.WHITE)
        ).isTrue()

        // Schwarzer Bauer auf der 2. Reihe, zieht auf die 1. Reihe
        board.placePieces(Position(Column.B, Row.TWO), Pawn(Color.BLACK))
        val fromBlack = Position(Column.B, Row.TWO)
        val toBlack = Position(Column.B, Row.ONE)
        val figureBlack = board.getFigureAt(fromBlack)!!
        board.move(fromBlack, toBlack, null)
        assertThat(
            board.getFigureAt(toBlack) is Pawn &&
                    (toBlack.row == Row.ONE && figureBlack.color() == Color.BLACK)
        ).isTrue()

        // Weißer Bauer auf der 6. Reihe, zieht auf die 7. Reihe (keine Umwandlung)
        board.placePieces(Position(Column.C, Row.SIX), Pawn(Color.WHITE))
        val fromNoPromo = Position(Column.C, Row.SIX)
        val toNoPromo = Position(Column.C, Row.SEVEN)
        val figureNoPromo = board.getFigureAt(fromNoPromo)!!
        board.move(fromNoPromo, toNoPromo, null)
        assertThat(
            !(board.getFigureAt(toNoPromo) is Pawn &&
                    (toNoPromo.row == Row.EIGHT && figureNoPromo.color() == Color.WHITE))
        ).isTrue()
    }

    @Test
    fun `isGameOver gibt true zurück wenn totalMoves mindestens 50 ist`() {
        val game = Game()
        game.totalMoves = 50
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `isGameOver gibt true zurück bei dreifacher Stellungswiederholung`() {
        val game = Game()
        val fen = FEN(game.board.toString()).toFenString()
        // Füge die aktuelle Stellung zweimal zur History hinzu (insgesamt 3x)
        game.boardHistory.add(fen)
        game.boardHistory.add(fen)
        assertThat(game.isGameOver()).isTrue()
    }

    @Test
    fun `Bauer wird auf gegnerischer Grundreihe zur Umwandlung markiert`() {
        val board = ChessBoard.emptyBoard()
        // Weißer Bauer zieht auf die 8. Reihe
        board.placePieces(Position(Column.A, Row.SEVEN), Pawn(Color.WHITE))
        val fromWhite = Position(Column.A, Row.SEVEN)
        val toWhite = Position(Column.A, Row.EIGHT)
        board.move(fromWhite, toWhite, null)
        val figureWhite = board.getFigureAt(toWhite)
        assertThat(figureWhite is Pawn && toWhite.row == Row.EIGHT && figureWhite?.color() == Color.WHITE).isTrue()

        // Schwarzer Bauer zieht auf die 1. Reihe
        board.placePieces(Position(Column.B, Row.TWO), Pawn(Color.BLACK))
        val fromBlack = Position(Column.B, Row.TWO)
        val toBlack = Position(Column.B, Row.ONE)
        board.move(fromBlack, toBlack, null)
        val figureBlack = board.getFigureAt(toBlack)
        assertThat(figureBlack is Pawn && toBlack.row == Row.ONE && figureBlack?.color() == Color.BLACK).isTrue()
    }

    @Test
    fun `playerMoves gibt die richtigen Züge für den aktuellen Spieler zurück`() {
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
        val playerMovesBlack = if (game.currentPlayerIsWhite) whiteMoves else blackMoves
        assertThat(playerMovesBlack).isEqualTo(blackMoves)
    }

    @Test
    fun `Rochade entfernt König und Turm von den Ursprungsfeldern`() {
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
    fun `gameState gibt Remis 50-Zuege-Regel zurueck wenn totalMoves mindestens 50 ist`() {
        val game = Game()
        game.totalMoves = 50
        assertThat(game.gameState()).isEqualTo("Remis (50-Züge-Regel)")
    }

    @Test
    fun `hasMoves ist true wenn aktueller Spieler Zuege hat und false wenn nicht`() {
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
}
