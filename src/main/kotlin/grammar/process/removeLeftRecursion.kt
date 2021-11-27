package grammar.process

import grammar.Grammar
import grammar.Rule
import grammar.Symbol

fun removeLeftRecursion(grammar: Grammar) {
    val symbolList: List<Symbol> = grammar.nonterminalSymbols.toList()
    val hasRemoveSymbolList: MutableList<Symbol> = mutableListOf<Symbol>().apply { addAll(grammar.nonterminalSymbols) }

    for (symbol in symbolList) {
        hasRemoveSymbolList.remove(symbol)
        for (hasRemoveSymbol in hasRemoveSymbolList) {
            replace(symbol, hasRemoveSymbol, grammar)
        }
        removeDirectLeftRecursion(symbol, grammar)
    }
}

fun replace(leftSymbol: Symbol, rightSymbol: Symbol, grammar: Grammar) {

    val toReplaceRuleList: MutableList<Rule> = getToReplaceRuleList(grammar, leftSymbol, rightSymbol)

    for (rule in toReplaceRuleList) {
        grammar.ruleManager.deleteRule(rule)
    }

    val newRules: List<Rule> = getNewRulesToReplace(grammar, rightSymbol, toReplaceRuleList, leftSymbol)

    grammar.ruleManager.addRules(newRules)
}

private fun getNewRulesToReplace(
    grammar: Grammar,
    rightSymbol: Symbol,
    toReplaceRuleList: MutableList<Rule>,
    leftSymbol: Symbol
): List<Rule> {
    val newRules: MutableList<Rule> = mutableListOf()
    val replaceRuleList: MutableSet<Rule> = grammar.ruleManager.getRulesBySymbol(rightSymbol)
    for (rule in toReplaceRuleList) {
        val endOfRight = rule.getRightExceptStart()
        for (replaceRule in replaceRuleList) {
            val newRule = Rule(leftSymbol, (replaceRule.right + endOfRight) as MutableList<Symbol>)
            newRules.add(newRule)
        }
    }
    return newRules
}

fun removeDirectLeftRecursion(symbol: Symbol, grammar: Grammar) {

    val rules = grammar.ruleManager.getRulesBySymbol(symbol)

    if (!hasLeftRecursion(rules)) return

    for (rule in rules) {
        grammar.ruleManager.deleteRule(rule)
    }

    val newRules = getNewRules(symbol, rules)

    grammar.ruleManager.addRules(newRules)

    val newSymbols = mutableListOf<Symbol>()

    for (rule in newRules) {
        newSymbols.add(rule.left)
    }

    grammar.addNonterminalSymbols(newSymbols)
}

fun getNewRules(symbol: Symbol, rules: MutableSet<Rule>): List<Rule> {
    val newSymbol = Symbol("${symbol.char}'")

    val newRules = mutableListOf<Rule>(Rule(newSymbol, mutableListOf(Symbol("@"))))

    val noRecursiveRules = rules.filter { it.right[0] != symbol }
    val recursiveRules = rules.filter { it.right[0] == symbol }

    for (rule in noRecursiveRules) {
        val newRule = Rule(symbol, (rule.right + newSymbol).toMutableList())
        newRules.add(newRule)
    }

    for (rule in recursiveRules) {
        val newRule = Rule(newSymbol, (rule.getRightExceptStart() + newSymbol).toMutableList())
        newRules.add(newRule)
    }

    return newRules
}

fun hasLeftRecursion(rules: MutableSet<Rule>): Boolean {
    for (rule in rules) {
        if (rule.left == rule.right[0]) return true
    }
    return false
}

private fun getToReplaceRuleList(
    grammar: Grammar,
    leftSymbol: Symbol,
    rightSymbol: Symbol,
): MutableList<Rule> {
    val toReplaceRuleList: MutableList<Rule> = mutableListOf()
    val leftRuleSet: MutableSet<Rule> = grammar.ruleManager.getRulesBySymbol(leftSymbol)

    for (rule in leftRuleSet) {
        if (rule.right[0] == rightSymbol) {
            toReplaceRuleList.add(rule)
        }
    }
    return toReplaceRuleList
}
