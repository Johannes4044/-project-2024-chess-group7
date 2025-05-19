package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FiguresTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val pawnExample = Pawn(true)
        assertThat(pawnExample.symbol()).isEqualTo("b")
        assertThat(pawnExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Rook gets correctly created`() {
        val rookExample = Rook(true)
        assertThat(rookExample.symbol()).isEqualTo("t")
        assertThat(rookExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Knight gets correctly created`() {
        val knightExample = Knight(true)
        assertThat(knightExample.symbol()).isEqualTo("s")
        assertThat(knightExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Bishop gets correctly created`() {
        val bishopExample = Bishop(true)
        assertThat(bishopExample.symbol()).isEqualTo("l")
        assertThat(bishopExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `Queen gets correctly created`() {
        val queenExample = Queen(true)
        assertThat(queenExample.symbol()).isEqualTo("d")
        assertThat(queenExample.isWhite).isEqualTo(true)
    }

    @Test
    fun `King gets correctly created`() {
        val kingExample = King(true)
        assertThat(kingExample.symbol()).isEqualTo("k")
        assertThat(kingExample.isWhite).isEqualTo(true)
    }
}