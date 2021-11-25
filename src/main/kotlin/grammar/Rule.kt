package grammar

/**
 * @author Extas
 * @date 2021/11/23 20:45
 */
class Rule(val left: Symbol, val right: MutableList<Symbol>) {

    val first = mutableSetOf<Symbol>()

    fun addFirst(first: Collection<Symbol>) {
        this.first.addAll(first)
    }

    fun getRightExceptStart(): List<Symbol> = right.subList(1, right.size)

    fun getRightByIndex(start: Int, end: Int): List<Symbol> = right.subList(start, end)

    override fun toString(): String {
        return "$left -> ${right.joinToString(" ")}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rule

        if (left != other.left) return false
        if (right != other.right) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + right.hashCode()
        return result
    }

    fun printFirst() {
        println(toString() + "; First = ${first.joinToString(" ")}")
    }
}