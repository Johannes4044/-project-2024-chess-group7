package hwr.oop

fun initializeBoard(board: Array<Array<String>>) {
    val bauernW = mutableListOf<Bauer>()
    val bauernB = mutableListOf<Bauer>()
    for (i in 0..7) {
        bauernW.add(Bauer(6, i, "weiss", true, "b"))
        bauernB.add(Bauer(1, i, "schwarz", true, "B"))
    }

    val laeuferW = mutableListOf<Laeufer>()
    laeuferW.add(Laeufer(7, 2, "weiss", true, "l"))
    laeuferW.add(Laeufer(7, 5, "weiss", true, "l"))

    val laeuferB = mutableListOf<Laeufer>()
    laeuferB.add(Laeufer(0, 2, "schwarz", true, "L"))
    laeuferB.add(Laeufer(0, 5, "schwarz", true, "L"))

    val SpringerW = mutableListOf<Springer>()
    SpringerW.add(Springer(7, 1, "weiss", true, "s"))
    SpringerW.add(Springer(7, 6, "weiss", true, "s"))

    val SpringerB = mutableListOf<Springer>()
    SpringerB.add(Springer(0, 1, "schwarz", true, "S"))
    SpringerB.add(Springer(0, 6, "schwarz", true, "S"))

    val TurmW = mutableListOf<Turm>()
    TurmW.add(Turm(7, 0, "weiss", true, "t"))
    TurmW.add(Turm(7, 7, "weiss", true, "t"))

    val TurmB = mutableListOf<Turm>()
    TurmB.add(Turm(0, 0, "schwarz", true, "T"))
    TurmB.add(Turm(0, 7, "schwarz", true, "T"))

    val DameW = mutableListOf<Dame>()
    DameW.add(Dame(7, 3, "weiss", true, "d"))
    DameW.add(Dame(7, 6, "weiss", true, "d"))

    val DameB = mutableListOf<Dame>()
    DameB.add(Dame(0, 3, "schwarz", true, "D"))
    DameB.add(Dame(0, 6, "schwarz", true, "D"))

    for (i in 0..7) {
        board[bauernB[i].positionX][bauernB[i].positionY] = bauernB[i].Name
        board[bauernW[i].positionX][bauernW[i].positionY] = bauernW[i].Name
    }

    for (i in 0..1) {
        board[laeuferW[i].positionX][laeuferW[i].positionY] = laeuferW[i].Name
        board[laeuferB[i].positionX][laeuferB[i].positionY] = laeuferB[i].Name

        board[SpringerB[i].positionX][SpringerB[i].positionY] = SpringerB[i].Name
        board[SpringerW[i].positionX][SpringerW[i].positionY] = SpringerW[i].Name

        board[TurmB[i].positionX][TurmB[i].positionY] = TurmB[i].Name
        board[TurmW[i].positionX][TurmW[i].positionY] = TurmW[i].Name

        board[DameB[i].positionX][DameB[i].positionY] = DameB[i].Name
        board[DameW[i].positionX][DameW[i].positionY] = DameW[i].Name
    }

    for (i in 2 until 6) {
        for (j in 0 until 8) {
            board[i][j] = " "  // Empty spaces in the middle of the board
        }
    }
}