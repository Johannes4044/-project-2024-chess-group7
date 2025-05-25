import hwr.oop.figures.Bishop
import hwr.oop.figures.King
import hwr.oop.figures.Knight
import hwr.oop.figures.Pawn
import hwr.oop.figures.Queen
import hwr.oop.figures.Rook
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class FiguresTest : AnnotationSpec() {

    @ParameterizedTest
    @CsvSource(
        "Pawn,true,b",
        "Pawn,false,B",
        "Rook,true,t",
        "Rook,false,T",
        "Knight,true,s",
        "Knight,false,S",
        "Bishop,true,l",
        "Bishop,false,L",
        "Queen,true,d",
        "Queen,false,D",
        "King,true,k",
        "King,false,K"
    )
    fun `figure symbol and color are correct`(figureType: String, isWhite: Boolean, expectedSymbol: String) {
        val figure = when (figureType) {
            "Pawn" -> Pawn(isWhite)
            "Rook" -> Rook(isWhite)
            "Knight" -> Knight(isWhite)
            "Bishop" -> Bishop(isWhite)
            "Queen" -> Queen(isWhite)
            "King" -> King(isWhite)
            else -> throw IllegalArgumentException("Unknown figure type")
        }
        assertThat(figure.symbol()).isEqualTo(expectedSymbol)
        assertThat(figure.isWhite).isEqualTo(isWhite)
    }
}