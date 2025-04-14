package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class FigurenTest : AnnotationSpec() {

    @Test
    fun `Pawn gets correctly created`() {
        val Bauerexample = Bauer(0, 1, "Weiß", true, "b")
        assertThat(Bauerexample.positionX).isEqualTo(0)
        assertThat(Bauerexample.positionY).isEqualTo(1)
        assertThat(Bauerexample.Farbe).isEqualTo("Weiß")
        assertThat(Bauerexample.istErsterZug).isEqualTo(true)
        assertThat(Bauerexample.Name).isEqualTo("b")
    }

    @Test
    fun `Rook gets correctly created`() {
        val Turmexample = Turm(0, 1, "Weiß", true, "T")
        assertThat(Turmexample.positionX).isEqualTo(0)
        assertThat(Turmexample.positionY).isEqualTo(1)
        assertThat(Turmexample.Farbe).isEqualTo("Weiß")
        assertThat(Turmexample.istErsterZug).isEqualTo(true)
        assertThat(Turmexample.Name).isEqualTo("T")
    }

    @Test
    fun `Night gets correctly created`() {
        val Nightexample = Springer(0, 1, "Weiß", true, "S")
        assertThat(Nightexample.positionX).isEqualTo(0)
        assertThat(Nightexample.positionY).isEqualTo(1)
        assertThat(Nightexample.Farbe).isEqualTo("Weiß")
        assertThat(Nightexample.istErsterZug).isEqualTo(true)
        assertThat(Nightexample.Name).isEqualTo("S")
    }

    @Test
    fun `Bishop gets correctly created`() {
        val Laeuferexample = Laeufer(0, 1, "Weiß", true, "l")
        assertThat(Laeuferexample.positionX).isEqualTo(0)
        assertThat(Laeuferexample.positionY).isEqualTo(1)
        assertThat(Laeuferexample.Farbe).isEqualTo("Weiß")
        assertThat(Laeuferexample.istErsterZug).isEqualTo(true)
        assertThat(Laeuferexample.Name).isEqualTo("l")
    }

    @Test
    fun `Queen gets correctly created`() {
        val Dameexample = Dame(0, 1, "Weiß", true, "D")
        assertThat(Dameexample.positionX).isEqualTo(0)
        assertThat(Dameexample.positionY).isEqualTo(1)
        assertThat(Dameexample.Farbe).isEqualTo("Weiß")
        assertThat(Dameexample.istErsterZug).isEqualTo(true)
        assertThat(Dameexample.Name).isEqualTo("D")
    }

    @Test
    fun `King gets correctly created`() {
        val Königexample = Bauer(0, 1, "Weiß", true, "k")
        assertThat(Königexample.positionX).isEqualTo(0)
        assertThat(Königexample.positionY).isEqualTo(1)
        assertThat(Königexample.Farbe).isEqualTo("Weiß")
        assertThat(Königexample.istErsterZug).isEqualTo(true)
        assertThat(Königexample.Name).isEqualTo("k")
    }

}