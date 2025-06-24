package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ChessBoardTests : AnnotationSpec() {
    @Test
    fun `initialize Chessboard does not return false`() {
        val chessBoardExample = ChessBoard.fullBoard()
        assertThat(chessBoardExample).isNotNull
    }

    @Test
    fun `FEN string is parsed correctly for full board`() {
        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst w KQkq - 0 1"
        val fen = FEN(fenString)
        assertThat(fen.getPiecePlacement()).isEqualTo("TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst")
        assertThat(fen.getActiveColor()).isEqualTo("w")
        assertThat(fen.getCastlingAvailability()).isEqualTo("KQkq")
        assertThat(fen.getEnPassantTarget()).isEqualTo("-")
        assertThat(fen.getHalfMoveClock()).isEqualTo(0)
        assertThat(fen.getFullMoveNumber()).isEqualTo(1)
    }

    @Test
    fun `Promotion works for every white figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position(Column.A, Row.EIGHT), Pawn(Color.WHITE))
        chessBoard.placePieces(Position(Column.B, Row.EIGHT), Pawn(Color.WHITE))
        chessBoard.placePieces(Position(Column.C, Row.EIGHT), Pawn(Color.WHITE))
        chessBoard.placePieces(Position(Column.D, Row.EIGHT), Pawn(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position(Column.A, Row.EIGHT), rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.A, Row.EIGHT))?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position(Column.B, Row.EIGHT), knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.B, Row.EIGHT))?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position(Column.C, Row.EIGHT), bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.C, Row.EIGHT))?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position(Column.D, Row.EIGHT), queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.EIGHT))?.symbol()).isEqualTo("d")
    }

    @Test
    fun `Promotion works for every black figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position(Column.A, Row.ONE), Pawn(Color.BLACK))
        chessBoard.placePieces(Position(Column.B, Row.ONE), Pawn(Color.BLACK))
        chessBoard.placePieces(Position(Column.C, Row.ONE), Pawn(Color.BLACK))
        chessBoard.placePieces(Position(Column.D, Row.ONE), Pawn(Color.BLACK))

        assertThat(chessBoard.promoteFigure(Position(Column.A, Row.ONE), rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.A, Row.ONE))?.symbol()).isEqualTo("T")
        assertThat(chessBoard.promoteFigure(Position(Column.B, Row.ONE), knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.B, Row.ONE))?.symbol()).isEqualTo("S")
        assertThat(chessBoard.promoteFigure(Position(Column.C, Row.ONE), bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.C, Row.ONE))?.symbol()).isEqualTo("L")
        assertThat(chessBoard.promoteFigure(Position(Column.D, Row.ONE), queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.ONE))?.symbol()).isEqualTo("D")
    }

    @Test
    fun`Promotion does not work for non-pawn figures`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        chessBoard.placePieces(Position(Column.A, Row.EIGHT), Rook(Color.WHITE))
        chessBoard.placePieces(Position(Column.B, Row.EIGHT), Knight(Color.WHITE))
        chessBoard.placePieces(Position(Column.C, Row.EIGHT), Bishop(Color.WHITE))
        chessBoard.placePieces(Position(Column.D, Row.EIGHT), Queen(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position(Column.A, Row.EIGHT), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.A, Row.EIGHT))?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position(Column.B, Row.EIGHT), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.B, Row.EIGHT))?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position(Column.C, Row.EIGHT), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.C, Row.EIGHT))?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position(Column.D, Row.EIGHT), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.EIGHT))?.symbol()).isEqualTo("d")
    }

    @Test
    fun`Promotion on empty board returns false`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        assertThat(chessBoard.promoteFigure(Position(Column.A, Row.EIGHT), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position(Column.A, Row.EIGHT))).isNull()
    }

    @Test
    fun `The row enum contains all rows from 1 to 8`() {
        val expectedRows = listOf(1,2,3,4,5,6,7,8)
        val actualRows = Row.values().map { it.row }
        assertThat(actualRows).containsExactlyElementsOf(expectedRows)
    }

    @Test
    fun `The name of the row enum matches its value`() {
        assertThat(Row.ONE.row).isEqualTo(1)
        assertThat(Row.EIGHT.row).isEqualTo(8)
    }

    @Test
    fun `from returns null when invalid row`() {
        val column = Column.values().first().column
        val invalidRow = -1
        val position = Position.from(column, invalidRow)
        assertNull(position)
    }

    @Test
    fun `The column enum contains all columns from a to h`() {
        val expectedColumns = listOf('a','b','c','d','e','f','g','h')
        val actualColumns = Column.values().map { it.column }
        assertThat(actualColumns).containsExactlyElementsOf(expectedColumns)
    }

    @Test
    fun `The name of the Column enum matches its value`() {
        assertThat(Column.A.column).isEqualTo('a')
        assertThat(Column.H.column).isEqualTo('h')
    }

    @Test
    fun `A pawn can be promoted to a pawn or a king`() {
            val chessBoard = ChessBoard.emptyBoard()
            chessBoard.placePieces(Position(Column.A, Row.EIGHT), Pawn(Color.WHITE))

            // Beförderung zu Bauer
            assertThat(chessBoard.promoteFigure(Position(Column.A, Row.EIGHT), FigureType.Pawn)).isTrue()
            assertThat(chessBoard.getFigureAt(Position(Column.A, Row.EIGHT))).isInstanceOf(Pawn::class.java)

            // Beförderung zu König
            assertThat(chessBoard.promoteFigure(Position(Column.A, Row.EIGHT), FigureType.King)).isTrue()
            assertThat(chessBoard.getFigureAt(Position(Column.A, Row.EIGHT))).isInstanceOf(King::class.java)
        }

    @Test
    fun `The move method returns false if the starting square is empty`() {
        val chessBoard = ChessBoard.emptyBoard()
        val from = Position(Column.A, Row.ONE)
        val to = Position(Column.A, Row.TWO)
        val result = chessBoard.move(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `fromFEN returns an empty chessboard`() {
        val fenString = "8/8/8/8/8/8/8/8 w - - 0 1"
        val chessBoard = ChessBoard.fromFEN(fenString)
        // Es sollten keine Figuren auf dem Brett sein
        assertThat(chessBoard.getAllPositions()).isEmpty()
    }

    @Test
    fun `enPassantTarget is set correctly after pawn double move`() {
        val chessBoard = ChessBoard.emptyBoard()
        val from = Position(Column.E, Row.TWO)
        val to = Position(Column.E, Row.FOUR)
        chessBoard.placePieces(from, Pawn(Color.WHITE))
        chessBoard.move(from, to)
        assertThat(chessBoard.enPassantTarget).isEqualTo(Position(Column.E, Row.THREE))
    }

    @Test
    fun `enPassantTarget is null after non-pawn move`() {
        val chessBoard = ChessBoard.emptyBoard()
        val from = Position(Column.A, Row.ONE)
        val to = Position(Column.A, Row.TWO)
        chessBoard.placePieces(from, Rook(Color.WHITE))
        chessBoard.move(from, to)
        assertThat(chessBoard.enPassantTarget).isNull()
    }

    @Test
    fun `en passant capture removes captured pawn`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Pawn(Color.WHITE))
        chessBoard.placePieces(Position(Column.D, Row.SEVEN), Pawn(Color.BLACK))

        // Schwarzer Bauer Doppelzug
        chessBoard.move(Position(Column.D, Row.SEVEN), Position(Column.D, Row.FIVE))
        // Weißer Bauer schlägt en passant
        val result = chessBoard.move(Position(Column.E, Row.FIVE), Position(Column.D, Row.SIX))

        assertThat(result).isTrue()
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.FIVE))).isNull()
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.SIX))).isInstanceOf(Pawn::class.java)
    }

    @Test
    fun `en passant is only possible immediately after double pawn move`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position(Column.E, Row.FIVE), Pawn(Color.WHITE))
        chessBoard.placePieces(Position(Column.D, Row.SEVEN), Pawn(Color.BLACK))

        chessBoard.move(Position(Column.D, Row.SEVEN), Position(Column.D, Row.FIVE))
        val enPassantResult = chessBoard.move(Position(Column.E, Row.FIVE), Position(Column.D, Row.SIX))
        assertThat(enPassantResult).isTrue()

        // Nochmal versuchen, aber ohne Doppelzug
        chessBoard.placePieces(Position(Column.D, Row.SEVEN), Pawn(Color.BLACK))
        chessBoard.move(Position(Column.D, Row.SEVEN), Position(Column.D, Row.SIX))
        val invalidEnPassant = chessBoard.move(Position(Column.E, Row.SIX), Position(Column.D, Row.SEVEN))
        assertThat(invalidEnPassant).isFalse()
    }

    @Test
    fun `move returns false for an invalid move`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position(Column.A, Row.ONE), Rook(Color.WHITE))
        // Turm versucht, diagonal zu ziehen (ungültig)
        val result = chessBoard.move(Position(Column.A, Row.ONE), Position(Column.B, Row.TWO))
        assertThat(result).isFalse()
    }

    @Test
    fun `findKing returns the position of the white king when whiteTurn is true`() {
        val chessBoard = ChessBoard.emptyBoard()
        val whiteKingPos = Position(Column.E, Row.ONE)
        chessBoard.placePieces(whiteKingPos, King(Color.WHITE))
        val result = chessBoard.findKing(true)
        assertThat(result).isEqualTo(whiteKingPos)
    }

    @Test
    fun `findKing returns the position of the black king when whiteTurn is false`() {
        val chessBoard = ChessBoard.emptyBoard()
        val blackKingPos = Position(Column.E, Row.EIGHT)
        chessBoard.placePieces(blackKingPos, King(Color.BLACK))
        val result = chessBoard.findKing(false)
        assertThat(result).isEqualTo(blackKingPos)
    }

    @Test
    fun `findKing returns null if no king of the specified color is found`() {
        val chessBoard = ChessBoard.emptyBoard()
        // Nur ein weißer König auf dem Brett
        chessBoard.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        val foundBlackKing = chessBoard.findKing(false)
        assertThat(foundBlackKing).isNull()

        // Nur ein schwarzer König auf dem Brett
        val chessBoard2 = ChessBoard.emptyBoard()
        chessBoard2.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        val foundWhiteKing = chessBoard2.findKing(true)
        assertThat(foundWhiteKing).isNull()
    }

    @Test
    fun `findKing returns null if there is no king on the board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val resultWhite = chessBoard.findKing(true)
        val resultBlack = chessBoard.findKing(false)
        assertThat(resultWhite).isNull()
        assertThat(resultBlack).isNull()
    }

    @Test
    fun `findKing returns the position of the correct king when both kings are present`() {
        val chessBoard = ChessBoard.emptyBoard()
        val whiteKingPos = Position(Column.E, Row.ONE)
        val blackKingPos = Position(Column.E, Row.EIGHT)
        chessBoard.placePieces(whiteKingPos, King(Color.WHITE))
        chessBoard.placePieces(blackKingPos, King(Color.BLACK))
        assertThat(chessBoard.findKing(true)).isEqualTo(whiteKingPos)
        assertThat(chessBoard.findKing(false)).isEqualTo(blackKingPos)
    }

    @Test
    fun `getFigureAt gibt null zurück wenn kein Stein auf dem Feld steht`() {
        val board = ChessBoard.emptyBoard()
        val pos = Position(Column.A, Row.ONE)
        assertThat(board.getFigureAt(pos)).isNull()
    }

    @Test
    fun `placePieces setzt eine Figur korrekt auf das Feld`() {
        val board = ChessBoard.emptyBoard()
        val pos = Position(Column.B, Row.TWO)
        board.placePieces(pos, Knight(Color.WHITE))
        assertThat(board.getFigureAt(pos)).isInstanceOf(Knight::class.java)
    }

    @Test
    fun `removePiece entfernt die Figur vom Feld`() {
        val board = ChessBoard.emptyBoard()
        val pos = Position(Column.C, Row.THREE)
        board.placePieces(pos, Bishop(Color.BLACK))
        board.removePiece(pos)
        assertThat(board.getFigureAt(pos)).isNull()
    }

    @Test
    fun `move verschiebt eine Figur und leert das Ursprungsfeld`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.D, Row.FOUR)
        val to = Position(Column.D, Row.FIVE)
        board.placePieces(from, Queen(Color.WHITE))
        board.move(from, to)
        assertThat(board.getFigureAt(to)).isInstanceOf(Queen::class.java)
        assertThat(board.getFigureAt(from)).isNull()
    }

    @Test
    fun `getAllFigures gibt leere Liste zurück wenn keine Figuren vorhanden sind`() {
        val board = ChessBoard.emptyBoard()
        assertThat(board.getAllFigures(true)).isEmpty()
        assertThat(board.getAllFigures(false)).isEmpty()
    }

    @Test
    fun `getAllPositions gibt alle belegten Felder zurück`() {
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        board.placePieces(Position(Column.E, Row.EIGHT), King(Color.BLACK))
        val positions = board.getAllPositions()
        assertThat(positions).containsExactlyInAnyOrder(
            Position(Column.E, Row.ONE),
            Position(Column.E, Row.EIGHT)
        )
    }

    @Test
    fun `move gibt false zurück wenn kein Stein auf Ursprungsfeld steht`() {
        val board = ChessBoard.emptyBoard()
        val from = Position(Column.A, Row.ONE)
        val to = Position(Column.A, Row.TWO)
        val result = board.move(from, to)
        assertThat(result).isFalse()
    }

    @Test
    fun `findKing gibt Position des weißen Königs zurück wenn weiß am Zug ist`() {
        val board = ChessBoard.emptyBoard()
        board.placePieces(Position(Column.E, Row.ONE), King(Color.WHITE))
        val result = board.findKing(true)
        assertThat(result).isEqualTo(Position(Column.E, Row.ONE))
    }

    @Test
    fun `findKing gibt null zurück wenn kein König gefunden wird`() {
        val board = ChessBoard.emptyBoard()
        val result = board.findKing(true)
        assertThat(result).isNull()
    }

    @Test
    fun `en passant schlägt fehl wenn kein Bauer zu schlagen ist`() {
        val board = ChessBoard.emptyBoard()
        // Schwarzer Bauer auf e5
        board.placePieces(Position(Column.E, Row.FIVE), Pawn(Color.BLACK))
        // Setze enPassantTarget, aber kein Bauer auf d5
        board.enPassantTarget = Position(Column.D, Row.SIX)
        val result = board.move(Position(Column.E, Row.FIVE), Position(Column.D, Row.SIX))
        assertThat(result).isFalse()
    }
}