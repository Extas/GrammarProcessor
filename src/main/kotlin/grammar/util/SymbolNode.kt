package grammar.util

import grammar.Symbol

/**
 * @author Extas
 * @date 2021/11/23 21:36
 */
class SymbolNode(val symbol: Symbol) {
    var parent: SymbolNode? = null
    var children: MutableSet<SymbolNode> = mutableSetOf()

    fun addChild(child: SymbolNode): SymbolNode {
        child.parent = this
        if (children.add(child)) return child
        else return children.first { it.symbol == child.symbol }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SymbolNode

        if (symbol != other.symbol) return false
        if (parent != other.parent) return false

        return true
    }

    override fun hashCode(): Int {
        var result = symbol.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }
}