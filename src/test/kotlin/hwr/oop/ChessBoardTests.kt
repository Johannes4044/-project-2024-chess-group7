package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

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

    @Test
    fun `FEN string is parsed correctly for empty board`() {
        val fenString = "8/8/8/8/8/8/8/8 b - - 10 20"
        val fen = FEN(fenString)
        assertThat(fen.getPiecePlacement()).isEqualTo("8/8/8/8/8/8/8/8")
        assertThat(fen.getActiveColor()).isEqualTo("b")
        assertThat(fen.getCastlingAvailability()).isEqualTo("-")
        assertThat(fen.getEnPassantTarget()).isEqualTo("-")
        assertThat(fen.getHalfmoveClock()).isEqualTo(10)
        assertThat(fen.getFullmoveNumber()).isEqualTo(20)
    }

    @Test
    fun `FEN string is parsed correctly for custom board`() {
        val fenString = "8/8/8/8/8/2l5/1S6/t7 w - e3 5 7"
        val fen = FEN(fenString)
        assertThat(fen.getPiecePlacement()).isEqualTo("8/8/8/8/8/2l5/1S6/t7")
        assertThat(fen.getActiveColor()).isEqualTo("w")
        assertThat(fen.getCastlingAvailability()).isEqualTo("-")
        assertThat(fen.getEnPassantTarget()).isEqualTo("e3")
        assertThat(fen.getHalfmoveClock()).isEqualTo(5)
        assertThat(fen.getFullmoveNumber()).isEqualTo(7)
    }

    @Test
    fun `Promotion works for every white figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position.A8, Pawn(Color.WHITE))
        chessBoard.placePieces(Position.B8, Pawn(Color.WHITE))
        chessBoard.placePieces(Position.C8, Pawn(Color.WHITE))
        chessBoard.placePieces(Position.D8, Pawn(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position.A8, rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.A8)?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position.B8, knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.B8)?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position.C8, bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.C8)?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position.D8, queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.D8)?.symbol()).isEqualTo("d")
    }

    @Test
    fun `Promotion works for every black figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position.A1, Pawn(Color.BLACK))
        chessBoard.placePieces(Position.B1, Pawn(Color.BLACK))
        chessBoard.placePieces(Position.C1, Pawn(Color.BLACK))
        chessBoard.placePieces(Position.D1, Pawn(Color.BLACK))

        assertThat(chessBoard.promoteFigure(Position.A1, rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.A1)?.symbol()).isEqualTo("T")
        assertThat(chessBoard.promoteFigure(Position.B1, knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.B1)?.symbol()).isEqualTo("S")
        assertThat(chessBoard.promoteFigure(Position.C1, bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.C1)?.symbol()).isEqualTo("L")
        assertThat(chessBoard.promoteFigure(Position.D1, queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position.D1)?.symbol()).isEqualTo("D")
    }

    @Test
    fun`Promotion does not work for non-pawn figures`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        chessBoard.placePieces(Position.A8, Rook(Color.WHITE))
        chessBoard.placePieces(Position.B8, Knight(Color.WHITE))
        chessBoard.placePieces(Position.C8, Bishop(Color.WHITE))
        chessBoard.placePieces(Position.D8, Queen(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position.A8, rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position.A8)?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position.B8, rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position.B8)?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position.C8, rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position.C8)?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position.D8, rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position.D8)?.symbol()).isEqualTo("d")
    }

    @Test
    fun`Promotion on empty board returns false`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        assertThat(chessBoard.promoteFigure(Position.A8, rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position.A8)).isNull()
    }
 }

