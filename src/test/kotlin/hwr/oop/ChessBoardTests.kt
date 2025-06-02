package hwr.oop

import hwr.oop.figures.*
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

class ChessBoardTests : AnnotationSpec() {
    @Test
    fun `initialize Chessboard does not return false`() {
        val chessBoardExample = ChessBoard.fullBoard()
        assertThat(chessBoardExample.displayBoard()).isNotNull
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
        assertThat(chessBoard.getFigureAt(Position('d', 1))).isNull()
        assertThat(chessBoard.getFigureAt(Position('e', 2))).isNull()
    }

    @Test
    fun `FEN string is generated correctly for custom board`() {
        val chessBoard = ChessBoard.emptyBoard()
        chessBoard.placePieces(Position('a', 1), Rook(Color.WHITE))
        chessBoard.placePieces(Position('b', 2), Knight(Color.BLACK))
        chessBoard.placePieces(Position('c', 3), Bishop(Color.WHITE))

        val fenString = FEN().toFEN(chessBoard)
        assertThat(fenString).isEqualTo("8/8/8/8/8/2l5/1S6/t7")
    }

    @Test
    fun `FEN to board conversion works correctly`() {
        val fenString = "TSLDKLST/BBBBBBBB/8/8/8/8/bbbbbbbb/tsldklst"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position('d', 1))?.symbol()).isEqualTo("d")
        assertThat(chessBoard.getFigureAt(Position('e', 2))?.symbol()).isEqualTo("b")
    }

    @Test
    fun `FEN to board conversion works correctly for empty board`() {
        val fenString = "8/8/8/8/8/8/8/8"
        val chessBoard = ChessBoard.fromFEN(fenString)
        assertThat(chessBoard.getFigureAt(Position('d', 1))).isNull()
        assertThat(chessBoard.getFigureAt(Position('e', 2))).isNull()
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
         val position = Position('a', 1)
         assertThat(chessBoard.isSpaceFree(game, position, true)).isTrue()
     }

     @Test
     fun `IsSpaceFree returns false for occupied position`() {
         val game = Game()
         val chessBoard = ChessBoard.emptyBoard()
         val position = Position('a', 1)
         val position2 = Position('b', 2)
         chessBoard.placePieces(position, Rook(Color.WHITE))
         chessBoard.placePieces(position2, Rook(Color.BLACK))
         assertThat(chessBoard.isSpaceFree(game, Position('b', 1), false)).isFalse()
         assertThat(chessBoard.isSpaceFree(game, Position('b', 1), true)).isFalse()
     }

    @Test
    fun `Promotion works for every white figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position('a', 8), Pawn(Color.WHITE))
        chessBoard.placePieces(Position('b', 8), Pawn(Color.WHITE))
        chessBoard.placePieces(Position('c', 8), Pawn(Color.WHITE))
        chessBoard.placePieces(Position('d', 8), Pawn(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position('a', 8), rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('a', 8))?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position('b', 8), knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('b', 8))?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position('c', 8), bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('c', 8))?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position('d', 8), queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('d', 8))?.symbol()).isEqualTo("d")
    }

    @Test
    fun `Promotion works for every black figure`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        val knight = FigureType.Knight
        val bishop = FigureType.Bishop
        val queen = FigureType.Queen
        chessBoard.placePieces(Position('a', 1), Pawn(Color.BLACK))
        chessBoard.placePieces(Position('b', 1), Pawn(Color.BLACK))
        chessBoard.placePieces(Position('c', 1), Pawn(Color.BLACK))
        chessBoard.placePieces(Position('d', 1), Pawn(Color.BLACK))

        assertThat(chessBoard.promoteFigure(Position('a', 1), rook)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('a', 1))?.symbol()).isEqualTo("T")
        assertThat(chessBoard.promoteFigure(Position('b', 1), knight)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('b', 1))?.symbol()).isEqualTo("S")
        assertThat(chessBoard.promoteFigure(Position('c', 1), bishop)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('c', 1))?.symbol()).isEqualTo("L")
        assertThat(chessBoard.promoteFigure(Position('d', 1), queen)).isTrue()
        assertThat(chessBoard.getFigureAt(Position('d', 1))?.symbol()).isEqualTo("D")
    }

    @Test
    fun`Promotion does not work for non-pawn figures`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        chessBoard.placePieces(Position('a', 8), Rook(Color.WHITE))
        chessBoard.placePieces(Position('b', 8), Knight(Color.WHITE))
        chessBoard.placePieces(Position('c', 8), Bishop(Color.WHITE))
        chessBoard.placePieces(Position('d', 8), Queen(Color.WHITE))

        assertThat(chessBoard.promoteFigure(Position('a', 8), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position('a', 8))?.symbol()).isEqualTo("t")
        assertThat(chessBoard.promoteFigure(Position('b', 8), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position('b', 8))?.symbol()).isEqualTo("s")
        assertThat(chessBoard.promoteFigure(Position('c', 8), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position('c', 8))?.symbol()).isEqualTo("l")
        assertThat(chessBoard.promoteFigure(Position('d', 8), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position('d', 8))?.symbol()).isEqualTo("d")
    }

    @Test
    fun`Promotion on empty board returns false`() {
        val chessBoard = ChessBoard.emptyBoard()
        val rook = FigureType.Rook
        assertThat(chessBoard.promoteFigure(Position('a', 8), rook)).isFalse()
        assertThat(chessBoard.getFigureAt(Position('a', 8))).isNull()
    }
 }

