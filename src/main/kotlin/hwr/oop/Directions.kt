package hwr.oop

enum class Directions(val dx: Int, val dy: Int) {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1),
    Knight_UP_LEFT(-1,2),
    Knight_UP_RIGHT(1,2),
    Knight_DOWN_LEFT(-1,-2),
    Knight_DOWN_RIGHT(1,-2),
    Knight_LEFT_UP(-2,1),
    Knight_LEFT_DOWN(-2,-1),
    Knight_RIGHT_UP(2,1),
    Knight_RIGHT_DOWN(2,-1)
}