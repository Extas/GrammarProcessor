package grammar

import grammar.table.Table

/**
 * @author Extas
 * @date 2021/11/11 15:58
 */
class Grammar {
    lateinit var startSymbol: Symbol

    val table: Table = Table()

    val nonterminalSymbols = mutableSetOf<Symbol>()

    val terminalSymbols = mutableSetOf<Symbol>()

    val ruleManager = RuleManager()

    fun simplifiedGrammar() {
        removeHarmfulRules()
        removeUselessSymbols()
        removeNonterminalRules()
    }

    // 去除有害规则
    fun removeHarmfulRules() {
        val copyRules = ruleManager.getAllRules()
        val ruleToRemove = mutableSetOf<Rule>()

        for (rule in copyRules) {
            if (rule.right.size == 1 && rule.right[0].isNonTerminal) {
                ruleToRemove.add(rule)
            }
        }

        for (rule in ruleToRemove) {
            ruleManager.deleteRule(rule)
        }

        val newRules = mutableSetOf<Rule>()

        for (rule in ruleToRemove) {

            val rightSymbol = rule.right[0]
            val ruleToChange = mutableSetOf<Rule>()

            ruleToChange.addAll(ruleManager.getRulesBySymbol(rightSymbol))

            for (changedRule in ruleToChange) {
                ruleManager.deleteRule(rule)
                val newRule = Rule(rule.left, changedRule.right)
                newRules.add(newRule)
            }
        }

        for (newRule in newRules) {
            ruleManager.addRule(newRule)
        }
    }

    // 去除不可到达规则
    fun removeUselessSymbols() {
        var flag = true

        while (flag) {
            flag = false
            val copyRules = ruleManager.getAllRules()
            val symbolsToRemove = mutableSetOf<Symbol>()

            for (rule in copyRules) {
                if (rule.left != startSymbol && copyRules.all { !it.right.contains(rule.left) }) {
                    symbolsToRemove.add(rule.left)
                    flag = true // 如果有删除，则继续循环
                }
            }

            for (symbol in symbolsToRemove) {
                ruleManager.removeRuleBySymbol(symbol)
                nonterminalSymbols.remove(symbol)
            }
        }
    }

    // 去除不可终结规则
    private fun removeNonterminalRules() {
        val copyRules = ruleManager.getAllRules()
        // 可终结规则： 右侧都是可终结符
        val stoppableRules = mutableSetOf<Rule>()

        // 可终结的非终结符：是可终结规则的左侧
        val stoppableSymbols = mutableSetOf<Symbol>()

        while (true) {

            val oldRules: MutableSet<Rule> = mutableSetOf<Rule>().apply { addAll(stoppableRules) }

            for (rule in copyRules) {
                if (rule.right.all { it.isTerminal || it.isEpsilon || stoppableSymbols.contains(it) }) {
                    stoppableRules.add(rule)
                    stoppableSymbols.add(rule.left)
                }
            }

            if (oldRules == stoppableRules) {
                break
            }
        }

        copyRules.clear()
        copyRules.addAll(stoppableRules)

        ruleManager.deleteAllRules()
        ruleManager.addRules(copyRules)

        nonterminalSymbols.clear()
        nonterminalSymbols.addAll(stoppableSymbols)

        terminalSymbols.clear()
        terminalSymbols.also {
            for (rule in copyRules) {
                it.addAll(rule.right.filter { it.isTerminal })
            }
        }
    }

    fun addNonterminalSymbols(symbols: Symbol) {
        nonterminalSymbols.add(symbols)
    }

    fun getNonterminalSymbol(symbol: Symbol): Symbol {
        return nonterminalSymbols.find { it.char == symbol.char } ?: symbol
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
                grammar.ruleManager.addRule(Rule(leftSymbol, rightSymbols as MutableList<Symbol>))
            }
            grammar.startSymbol = grammar.nonterminalSymbols.first()
            return grammar
        }
    }
}