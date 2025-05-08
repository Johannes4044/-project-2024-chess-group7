package hwr.oop


import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ConsoleOutputTest : AnnotationSpec() {

    @Test
    fun `test console output`() {
        val originalOut = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        val chessBoardExample = ChessBoard.fullBoard()


        chessBoardExample.displayBoard()

        System.setOut(originalOut)

        val output = outputStream.toString()
        assertThat(output).contains("T S L D K L S T")
        assertThat(output).contains("B B B B B B B B")
        assertThat(output).contains(". . . . . . . . ")
        assertThat(output).contains(". . . . . . . . ")
        assertThat(output).contains(". . . . . . . . ")
        assertThat(output).contains(". . . . . . . . ")
        assertThat(output).contains("b b b b b b b b")
        assertThat(output).contains("t s l d k l s t")
        }
}