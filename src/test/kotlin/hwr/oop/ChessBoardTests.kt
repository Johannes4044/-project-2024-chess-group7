package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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
        assertThat(fen.getHalfmoveClock()).isEqualTo(0)
        assertThat(fen.getFullmoveNumber()).isEqualTo(1)
    }

//    @Test
//    fun `FEN to board conversion works correctly`() {
//        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
//        val chessBoard = ChessBoard.fromFEN(fenString)
//        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.EIGHT))?.symbol()).isEqualTo("D")
//        assertThat(chessBoard.getFigureAt(Position(Column.E, Row.SEVEN))?.symbol()).isEqualTo("B")
//    }
//
//    @Test
//    fun `FEN to board conversion works correctly for empty board`() {
//        val fenString = "8/8/8/8/8/8/8/8"
//        val chessBoard = ChessBoard.fromFEN(fenString)
//        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.EIGHT))).isNull()
//        assertThat(chessBoard.getFigureAt(Position(Column.E, Row.SEVEN))).isNull()
//    }

    @Test
    fun `fromFEN throws IllegalArgumentException for invalid FEN`() {
        val invalidFEN = "invalidFENString"

        val exception = assertThrows<IllegalArgumentException> {
            ChessBoard.fromFEN(invalidFEN)
        }

        assertThat(exception.message?.contains("Invalid figure in FEN") == true)
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
    fun `Row enum enthält alle Reihen von 1 bis 8`() {
        val expectedRows = listOf(1,2,3,4,5,6,7,8)
        val actualRows = Row.values().map { it.row }
        assertThat(actualRows).containsExactlyElementsOf(expectedRows)
    }

    @Test
    fun `Row enum name stimmt mit Wert überein`() {
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
    fun `Column enum enthält alle Spalten von a bis h`() {
        val expectedColumns = listOf('a','b','c','d','e','f','g','h')
        val actualColumns = Column.values().map { it.column }
        assertThat(actualColumns).containsExactlyElementsOf(expectedColumns)
    }

    @Test
    fun `Column enum name stimmt mit Wert überein`() {
        assertThat(Column.A.column).isEqualTo('a')
        assertThat(Column.H.column).isEqualTo('h')
    }
 }