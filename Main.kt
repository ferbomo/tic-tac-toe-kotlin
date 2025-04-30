package tictactoe

fun main() {
    val input = "_________";
    if (input.any { it != 'X' && it != 'O' && it != '_' }) {
        println("Invalid input. Use exactly 9 characters: X, O or _")
        return
    }

    val board: MutableList<MutableList<Char>> = input.chunked(3)
        .map { it.toMutableList() }
        .toMutableList()

    // Show board
    fun display(c: Char): Char {
        return if (c == '_') ' ' else c
    }

    fun showGameBoard() {
        println("\n---------")
        board.forEach { row ->
            println("| ${display(row[0])} ${display(row[1])} ${display(row[2])} |")
        }
        println("---------")
    }

    showGameBoard()

    fun isWinning(char: Char): Boolean {
        return listOf(
            Triple(board[0][0], board[0][1], board[0][2]),
            Triple(board[1][0], board[1][1], board[1][2]),
            Triple(board[2][0], board[2][1], board[2][2]),
            Triple(board[0][0], board[1][0], board[2][0]),
            Triple(board[0][1], board[1][1], board[2][1]),
            Triple(board[0][2], board[1][2], board[2][2]),
            Triple(board[0][0], board[1][1], board[2][2]),
            Triple(board[0][2], board[1][1], board[2][0])
        ).any { (a, b, c) -> a == char && b == char && c == char }
    }

    fun switchPlayer(char: Char): Char {
        return if (char == 'X') 'O' else 'X'
    }

    var player = 'O'

    // Ask user for input coordinates until the game is over
    while (true) {
        val coordinates: Array<String> = readln().split(" ").toTypedArray()
        if (coordinates.size != 2 || coordinates.any { it.toIntOrNull() == null }) {
            println("You should enter numbers!")
            continue
        }
        if (coordinates[0].toInt() !in 1..3 || coordinates[1].toInt() !in 1..3) {
            println("Coordinates should be from 1 to 3!")
            continue
        }
        if (board[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] != '_') {
            println("This cell is occupied! Choose another one!")
            continue
        } else { //coordinates are valid, so we place the game and check for win
            player = switchPlayer(player)
            board[coordinates[0].toInt() - 1][coordinates[1].toInt() - 1] = player
            showGameBoard()
            if (isWinning('X')) {
                println("X wins")
                break
            }
            if (isWinning('O')) {
                println("O wins")
                break
            }
            if (board.all { row -> row.all { it != '_' } }) {
                println("Draw")
                break
            }
        }
    }

}
