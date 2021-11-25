package grammar.table

import grammar.Grammar
import grammar.Rule
import grammar.Symbol

/**
 * @author Extas
 * @date 2021/11/25 16:56
 */
class Table {
    val table: MutableMap<Symbol, MutableMap<Symbol, Rule>> = mutableMapOf()

    fun buildTable(grammar: Grammar) {

        for (rule in grammar.ruleManager.getAllRules()) {

            val first = rule.first

            addRulesToTable(first, rule)

            if (first.contains(Symbol("@"))) {
                val follow = grammar.getNonterminalSymbol(rule.left).follow

                addRulesToTable(follow, rule)
                if (follow.contains(Symbol("$")))
                    addRuleToTable(rule, Symbol("$"))
            }
        }
    }

    private fun addRulesToTable(symbolSet: MutableSet<Symbol>, rule: Rule) {
        for (symbol in symbolSet) {
            if (symbol.isTerminal) {
                addRuleToTable(rule, symbol)
            }
        }
    }

    private fun addRuleToTable(rule: Rule, symbol: Symbol) {
        val row = table.getOrPut(rule.left) { mutableMapOf() }
        if (rule.right.size > (row.getOrPut(symbol) { rule }.right.size)) {
            row[symbol] = rule
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Table:\n")
        for (nonTerm in table.keys) {
            stringBuilder.append("$nonTerm -> ")
            for (Term in table[nonTerm]!!.keys) {
                stringBuilder.append("$Term: ")
                stringBuilder.append("${table[nonTerm]!![Term]} | ")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}