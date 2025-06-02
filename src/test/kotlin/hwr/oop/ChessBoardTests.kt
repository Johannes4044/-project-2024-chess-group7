package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

class ChessBoardTests : AnnotationSpec() {
    @Test
    fun `initialize Chessboard does not return false`() {
        val chessBoardExample = ChessBoard.fullBoard()
        assertThat(chessBoardExample).isNotNull
    }

    @Test
    fun `FEN string is generated correctly`() {
        val chessBoard = ChessBoard.fullBoard()
        val fenString = FEN().toFEN(chessBoard)
        assertThat(fenString).isEqualTo("TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst")
    }

    @Test
    fun `FEN string is generated correctly for empty board`() {
        val chessBoard = ChessBoard.emptyBoard()
        val fenString = FEN().toFEN(chessBoard)
        assertThat(fenString).isEqualTo("8/8/8/8/8/8/8/8")
        assertThat(chessBoard.getFigureAt(Position.D1)).isNull()
        assertThat(chessBoard.getFigureAt(Position.E2)).isNull()
    }

    @Test
    fun `FEN string is generated correctly for custom board`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position.A1, Rook(Color.WHITE))
        chessBoard.placePieces(Position.B2, Knight(Color.BLACK))
        chessBoard.placePieces(Position.C3, Bishop(Color.WHITE))

        val fenString = FEN().toFEN(chessBoard)
        assertThat(fenString).isEqualTo("8/8/8/8/8/2l5/1S6/t7")
    }

//    @Test
//    fun `FEN to board conversion works correctly`() {
//        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
//        val chessBoard = ChessBoard.fromFEN(fenString)
//        assertThat(chessBoard.getFigureAt(Position.D1)?.symbol()).isEqualTo("d")
//        assertThat(chessBoard.getFigureAt(Position.E2)?.symbol()).isEqualTo("b")
//    }
//
//    @Test
//    fun `FEN to board conversion works correctly for empty board`() {
//        val fenString = "8/8/8/8/8/8/8/8"
//        val chessBoard = ChessBoard.fromFEN(fenString)
//        assertThat(chessBoard.getFigureAt(Position.D1)).isNull()
//        assertThat(chessBoard.getFigureAt(Position.E2)).isNull()
//    }
//
//    @Test
//    fun `fromFEN throws IllegalArgumentException for invalid FEN`() {
//        val invalidFEN = "invalidFENString"
//
//        val exception = assertThrows<IllegalArgumentException> {
//            ChessBoard.fromFEN(invalidFEN)
//        }
//
//        assertThat(exception.message?.contains("Invalid figure in FEN") == true)
//    }

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

