package grammar.process

import grammar.Grammar
import grammar.Rule
import grammar.Symbol

/**
 * @author Extas
 * @date 2021/11/25 13:27
 */

fun generateFirst(grammar: Grammar) {
    for (rule in grammar.ruleManager.getAllRules()) {
        firstSet(rule, grammar)
    }
}

fun getSymbolFirst(symbol: Symbol, grammar: Grammar): Set<Symbol> {
    val first = mutableSetOf<Symbol>()
    for (rule in grammar.ruleManager.getRulesBySymbol(symbol)) {
        first.addAll(rule.first)
    }
    return first
}

fun firstSet(rule: Rule, grammar: Grammar): Set<Symbol> {
    if (rule.first.isNotEmpty())
        return rule.first

    val first = mutableSetOf<Symbol>()

    for ((i, symbol) in rule.right.withIndex()) {

        first.addAll(first(symbol, grammar))

        if (!first.contains(Symbol("@"))) break
        // first contains @
        if (i != rule.right.size - 1) {
            first.remove(Symbol("@"))
        }
    }

    rule.addFirst(first)
    return first
}

fun first(symbol: Symbol, grammar: Grammar): Collection<Symbol> {
    if (symbol.isTerminal || symbol.isEpsilon) return listOf(symbol)

    if (symbol.isNonTerminal) {
        val rules = grammar.ruleManager.getRulesBySymbol(symbol)
        return getRulesFirst(rules, grammar)
    }

    return emptyList()
}

fun getRulesFirst(rules: MutableSet<Rule>, grammar: Grammar): Collection<Symbol> {
    val first = mutableSetOf<Symbol>()

    rules.forEach { rule ->
        first.addAll(firstSet(rule, grammar))
    }

    return first
}
