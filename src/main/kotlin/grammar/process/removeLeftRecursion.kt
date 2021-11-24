package grammar.process

import grammar.Grammar
import grammar.Rule
import grammar.Symbol

fun removeLeftRecursion(grammar: Grammar) {
    val symbolList: List<Symbol> = grammar.nonterminalSymbols.toList()
    val hasRemoveSymbolList: MutableList<Symbol> = mutableListOf()

    for (symbol in symbolList) {
        for (hasRemoveSymbol in hasRemoveSymbolList) {
            replace(symbol, hasRemoveSymbol, grammar)
        }
        removeDirectLeftRecursion(symbol, grammar)
    }
}

fun replace(leftSymbol: Symbol, rightSymbol: Symbol, grammar: Grammar) {
    TODO("Not yet implemented")
}

fun removeDirectLeftRecursion(symbol: Symbol, grammar: Grammar) {

    val rules = grammar.ruleManager.getRulesBySymbol(symbol)
    val newRules = mutableSetOf<Rule>()

    for (rule in rules) {
        if (rule.left == rule.right[0]) {

        }
    }


}
