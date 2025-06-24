package hwr.oop

class FEN(fenString: String? = null) {
    private var piecePlacement: String = ""
    private var activeColor: String = ""
    private var castlingAvailability: String = ""
    private var enPassantTarget: String = ""
    private var halfMoveClock: Int = 0
    private var fullMoveNumber: Int = 1

    companion object {
        private const val DEFAULT_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    }

    init {
        parseFen(fenString?.takeIf { it.trim().split(" ").size == 6 } ?: DEFAULT_FEN)
    }

    private fun parseFen(fen: String) {
        val parts = fen.trim().split(" ")
        require(parts.size == 6) { "FEN-String muss aus 6 Teilen bestehen." }
        piecePlacement = parts[0]
        activeColor = parts[1]
        castlingAvailability = parts[2]
        enPassantTarget = parts[3]
        halfMoveClock = parts[4].toInt()
        fullMoveNumber = parts[5].toInt()
    }

    fun getPiecePlacement() = piecePlacement
    fun getActiveColor() = activeColor
    fun getCastlingAvailability() = castlingAvailability
    fun getEnPassantTarget() = enPassantTarget
    fun getHalfMoveClock() = halfMoveClock
    fun getFullMoveNumber() = fullMoveNumber

    fun toFenString(): String {
        return listOf(
            piecePlacement,
            activeColor,
            castlingAvailability,
            enPassantTarget,
            halfMoveClock.toString(),
            fullMoveNumber.toString()
        ).joinToString(" ")
    }
}