package hwr.oop.figures


/**
 * Enum representing the different types of chess pieces.
 */

enum class FigureType {
    Pawn,
    Rook,
    Knight,
    Bishop,
    Queen,
    King;
}


//enum class FigureType(val fenWhite: Char, val fenBlack: Char) {
//    Pawn('B', 'b'),
//    Rook('T', 't'),
//    Knight('S', 's'),
//    Bishop('L', 'l'),
//    Queen('D', 'd'),
//    King('K', 'k');
//
//    companion object {
//        fun fromFenChar(c: Char): Pair<FigureType, Color>? =
//            values().firstOrNull { it.fenWhite == c || it.fenBlack == c }?.let { type ->
//                if (type.fenWhite == c) type to Color.WHITE else type to Color.BLACK
//            }
//        fun toFenChar(type: FigureType, color: Color): Char =
//            if (color == Color.WHITE) type.fenWhite else type.fenBlack
//    }
//}