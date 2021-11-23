package grammar.util

import grammar.Symbol
import java.util.*

/**
 * @author Extas
 * @date 2021/11/23 21:36
 */
class SymbolTree {
    private val root = SymbolNode(Symbol("root"))

    fun buildTree(rightList: MutableList<List<Symbol>>) {

        for (right in rightList) {
            var currentNode = root

            for (symbol in right) {
                currentNode = currentNode.addChild(SymbolNode(symbol))
            }

            currentNode.addChild(SymbolNode(Symbol("$")))
        }
    }

    fun getLeftFactorsList(): MutableList<List<Symbol>> {
        val result = mutableListOf<List<Symbol>>()

        val resultStack = findNode()

        while (resultStack.isNotEmpty()) {
            val leftFactors = mutableListOf<Symbol>()

            var node = resultStack.pop()
            while (node != root) {
                leftFactors.add(node.symbol)
                node = node.parent
            }
            result.add(leftFactors)
        }

        return result
    }

    private fun findNode(): Stack<SymbolNode> {
        val queue = LinkedList<SymbolNode>()
        val resultStack = Stack<SymbolNode>()

        for (node in root.children) {
            queue.push(node)
        }

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()

            for (child in node.children) {
                queue.add(child)
            }

            if (node.children.size >= 2) {
                resultStack.push(node)
            }
        }

        return resultStack
    }
}