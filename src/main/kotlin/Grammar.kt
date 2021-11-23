/**
 * @author Extas
 * @date 2021/11/11 15:58
 */
class Grammar() {
    lateinit var startSymbol: Symbol

    val nonterminalSymbols = mutableSetOf<Symbol>()

    val terminalSymbols = mutableSetOf<Symbol>()

    val rules = mutableSetOf<Rule>()

    // 去除有害规则
    fun removeHarmfulRules() {
        val ruleToRemove = mutableSetOf<Rule>()

        for (rule in rules) {
            if (rule.right.size == 1 && rule.left == rule.right[0]) {
                ruleToRemove.add(rule)
            }
        }

        for (rule in ruleToRemove) {
            rules.removeAll { rule == it }

            // 移除的非终结符是否还有其他规则，没有的话，则移除这个非终结符
            var hasOther = false
            for (remainingRule in rules) {
                if (remainingRule.left == rule.right[0]) {
                    hasOther = true
                }
            }

            if (!hasOther) {
                nonterminalSymbols.remove(rule.right[0])
            }
        }
    }

    // 去除不可到达规则
    fun removeUselessSymbols() {
        val symbolsToRemove = mutableSetOf<Symbol>()

        var flag = true

        while (flag) {
            flag = false

            for (rule in rules) {
                if (rule.left != startSymbol && rules.all { !it.right.contains(rule.left) }) {
                    symbolsToRemove.add(rule.left)
                    flag = true // 如果有删除，则继续循环
                }
            }

            for (symbol in symbolsToRemove) {
                rules.removeAll { it.left == symbol }
                rules.removeAll { it.right.contains(symbol) }
                nonterminalSymbols.remove(symbol)
            }
        }
    }

    // 去除不可终止规则
    fun removeNonterminalRules() {
        val stoppableRules = mutableSetOf<Rule>()
        val stoppableSymbols = mutableSetOf<Symbol>()

        for (rule in rules) {
            if (rule.right.all { it.isTerminal || it.isEpsilon }) {
                stoppableRules.add(rule)
                stoppableSymbols.add(rule.left)
            }
        }

        
    }


    companion object {
        fun fromString(str: String): Grammar {
            val grammar = Grammar()
            val lines = str.split("\n")
            for (line in lines) {
                if (!line.contains("->")) continue
                val parts = line.split("->")
                val left = parts[0].trim()
                val right = parts[1].trim().toList()
                val leftSymbol = Symbol(left[0])
                val rightSymbols = right.map { Symbol(it) }
                grammar.nonterminalSymbols.add(leftSymbol)
                grammar.terminalSymbols.addAll(rightSymbols.filter { it.isTerminal })
                grammar.rules.add(Rule(leftSymbol, rightSymbols))
            }
            grammar.startSymbol = grammar.nonterminalSymbols.first()
            return grammar
        }
    }

    class Rule(val left: Symbol, val right: List<Symbol>) {
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
    }
}