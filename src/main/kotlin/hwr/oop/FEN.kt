package hwr.oop

class FEN(fenString: String? = null) {
    private var piecePlacement: String = ""
    private var activeColor: String = ""
    private var castlingAvailability: String = ""
    private var enPassantTarget: String = ""
    private var halfmoveClock: Int = 0
    private var fullmoveNumber: Int = 1
    init {
        fenString?.let { parseFen(it) }
    }
    private fun parseFen(fen: String) {
        val parts = fen.trim().split(" ")
        require(parts.size == 6) { "FEN-String muss aus 6 Teilen bestehen." }
        piecePlacement = parts[0]
        activeColor = parts[1]
        castlingAvailability = parts[2]
        enPassantTarget = parts[3]
        halfmoveClock = parts[4].toInt()
        fullmoveNumber = parts[5].toInt()
    }
    fun getPiecePlacement() = piecePlacement
    fun getActiveColor() = activeColor
    fun getCastlingAvailability() = castlingAvailability
    fun getEnPassantTarget() = enPassantTarget
    fun getHalfmoveClock() = halfmoveClock
    fun getFullmoveNumber() = fullmoveNumber
    fun toFenString(): String {
        return listOf(
            piecePlacement,
            activeColor,
            castlingAvailability,
            enPassantTarget,
            halfmoveClock.toString(),
            fullmoveNumber.toString()
        ).joinToString(" ")
    }
}
