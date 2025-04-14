package hwr.oop

import Laeufer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FigurenTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val Bauerexample = Bauer(true)
        assertThat(Bauerexample.symbol()).isEqualTo("b")
        assertThat(Bauerexample.istweiß).isEqualTo(true)
    }

    @Test
    fun `Rook gets correctly created`() {
        val Turmexample = Turm(true)
        assertThat(Turmexample.symbol()).isEqualTo("t")
        assertThat(Turmexample.istweiß).isEqualTo(true)
    }

    @Test
    fun `Night gets correctly created`() {
        val Springerexample = Springer(true)
        assertThat(Springerexample.symbol()).isEqualTo("s")
        assertThat(Springerexample.istweiß).isEqualTo(true)
    }

    @Test
    fun `Bishop gets correctly created`() {
        val Laeuferexample = Laeufer(true)
        assertThat(Laeuferexample.symbol()).isEqualTo("l")
        assertThat(Laeuferexample.istweiß).isEqualTo(true)
    }

    @Test
    fun `Queen gets correctly created`() {
        val Dameexample = Dame(true)
        assertThat(Dameexample.symbol()).isEqualTo("d")
        assertThat(Dameexample.istweiß).isEqualTo(true)
    }

    @Test
    fun `King gets correctly created`() {
        val Königexample = König(true)
        assertThat(Königexample.symbol()).isEqualTo("k")
        assertThat(Königexample.istweiß).isEqualTo(true)
    }

}