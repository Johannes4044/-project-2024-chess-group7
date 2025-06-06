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
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.ONE))).isNull()
        assertThat(chessBoard.getFigureAt(Position(Column.E, Row.TWO))).isNull()
    }

    @Test
    fun `FEN string is generated correctly for custom board`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position(Column.A, Row.ONE), Rook(Color.WHITE))
        chessBoard.placePieces(Position(Column.B, Row.TWO), Knight(Color.BLACK))
        chessBoard.placePieces(Position(Column.C, Row.THREE), Bishop(Color.WHITE))

        val fenString = FEN().toFEN(chessBoard)
        assertThat(fenString).isEqualTo("8/8/8/8/8/2l5/1S6/t7")
    }

    @Test
    fun `FEN to board conversion works correctly`() {
        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.ONE))?.symbol()).isEqualTo("d")
        assertThat(chessBoard.getFigureAt(Position(Column.E, Row.TWO))?.symbol()).isEqualTo("b")
    }

    @Test
    fun `FEN to board conversion works correctly for empty board`() {
        val fenString = "8/8/8/8/8/8/8/8"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position(Column.D, Row.ONE))).isNull()
        assertThat(chessBoard.getFigureAt(Position(Column.E, Row.TWO))).isNull()
    }

    @Test
    fun `fromFEN throws IllegalArgumentException for invalid FEN`() {
        val invalidFEN = "invalidFENString"

        val exception = assertThrows<IllegalArgumentException> {
            ChessBoard.fromFEN(invalidFEN)
        }

        assertThat(exception.message?.contains("Invalid figure in FEN") == true)
    }
  @Test
     fun `IsSpaceFree returns true for empty position`() {
         val game = Game()
         val chessBoard = ChessBoard.emptyBoard()
         val position = Position(Column.A, Row.ONE)
         assertThat(chessBoard.isSpaceFree(game, position, true)).isTrue()
     }

     @Test
     fun `IsSpaceFree returns false for occupied position`() {
         val game = Game()
         val chessBoard = ChessBoard.emptyBoard()
         val position = Position(Column.A, Row.ONE)
         val position2 = Position(Column.B, Row.TWO)
         chessBoard.placePieces(position, Rook(Color.WHITE))
         chessBoard.placePieces(position2, Rook(Color.BLACK))
         assertThat(chessBoard.isSpaceFree(game, Position(Column.B, Row.ONE), false)).isFalse()
         assertThat(chessBoard.isSpaceFree(game, Position(Column.B, Row.ONE), true)).isFalse()
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
 }

