package grammar

/**
 * @author Extas
 * @date 2021/11/11 15:55
 */
class Symbol(val char: String) {
    constructor(char: Char) : this(char.toString())

    val follow: MutableSet<Symbol> = mutableSetOf()

    val followToString: String
        get() = "\nSymbol: ${char}; " + (follow.joinToString(separator = " ") { it.char })

    fun derivedSymbols(time: Int): Symbol {
        val newChar = "${char}${time + 1}"
        return Symbol(newChar)
    }

    val isTerminal: Boolean
        get() = char[0] in 'a'..'z'

    val isNonTerminal: Boolean
        get() = char[0] in 'A'..'Z'

    val isEpsilon: Boolean
        get() = char[0] == '@'

    override fun toString(): String {
        return char
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Symbol

        if (char != other.char) return false

        return true
    }

    override fun hashCode(): Int {
        return char.hashCode()
    }
}