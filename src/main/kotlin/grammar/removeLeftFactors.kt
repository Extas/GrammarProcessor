package grammar

import grammar.util.SymbolTree

/**
 * @author Extas
 * @date 2021/11/23 20:41
 */

fun removeLeftFactors(grammar: Grammar) {

    for (symbol in grammar.nonterminalSymbols) {

        val rulesOfOneSymbol = grammar.getRulesBySymbol(symbol)

        val leftFactorsList = findLeftFactors(rulesOfOneSymbol)

        for ((i, leftFactors) in leftFactorsList.withIndex()) {
            if (leftFactors.isNotEmpty()) {
                // 将此非终结符的所有规则中含有左因子的，替换为新的非终结符
                changeRules(grammar, symbol, leftFactors, i)
            }
        }
    }
}

fun findLeftFactors(rules: MutableSet<Rule>): List<List<Symbol>> {

    val symbolTree = SymbolTree()

    val rightList = mutableListOf<List<Symbol>>().apply {
        for (rule in rules) {
            add(rule.right)
        }
    }

    symbolTree.buildTree(rightList)

    return symbolTree.getLeftFactorsList()
}

fun changeRules(grammar: Grammar, symbol: Symbol, leftFactors: List<Symbol>, time: Int) {
    val rulesOfOneSymbol = grammar.getRulesBySymbol(symbol)

    val needToChangeRules = findNeedToChangeRules(rulesOfOneSymbol, leftFactors)

    for (rule in needToChangeRules) {
        val newRule = Rule(symbol.derivedSymbols(time), rule.right.subList(leftFactors.size, rule.right.size))
        grammar.addRule(newRule)
        grammar.removeRule(rule)
    }

    val newRight = mutableListOf<Symbol>().apply {
        addAll(leftFactors)
        add(symbol.derivedSymbols(time))
    }
    grammar.addRule(Rule(symbol, newRight))
}

private fun findNeedToChangeRules(
    rulesOfOneSymbol: MutableSet<Rule>,
    leftFactors: List<Symbol>,
): MutableList<Rule> {
    val needToChangeRules = mutableListOf<Rule>()

    for (rule in rulesOfOneSymbol) {

        val right = rule.right

        if (right.size < leftFactors.size) {
            continue
        }

        var match = true

        for ((i, symbol) in leftFactors.withIndex()) {
            if (right[i] != symbol) {
                match = false
                break
            }
        }

        if (match) {
            needToChangeRules.add(rule)
        }
    }

    return needToChangeRules
}
