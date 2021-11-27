package grammar.process

import grammar.Grammar
import grammar.Rule
import grammar.Symbol

/**
 * @author Extas
 * @date 2021/11/25 15:26
 */

fun generateFollow(grammar: Grammar) {
    grammar.addFollow(grammar.startSymbol, setOf(Symbol("$")))

    var hasNew = true

    while (hasNew) {
        hasNew = false

        for (rule in grammar.ruleManager.getAllRules()) {

            for ((i, symbol) in rule.right.withIndex()) {

                if (symbol.isNonTerminal) {

                    // 第一、二种情况
                    hasNew = isNonTerminal(i, rule, grammar, symbol, hasNew)

                    // 第三种情况：是最后一个
                    if (i == rule.right.size - 1) {
                        if (grammar.addFollow(symbol, grammar.getFollow(rule.left))) {
                            hasNew = true
                        }
                    }
                }
            }
        }
    }
}

private fun isNonTerminal(
    i: Int,
    rule: Rule,
    grammar: Grammar,
    symbol: Symbol,
    hasNew: Boolean
): Boolean {
    var hasNew1 = hasNew
    // 第一种情况：不是最后一个
    if (i < rule.right.size - 1) {

        val fakeRule = Rule(Symbol("@"), mutableListOf<Symbol>().apply {
            for (j in (i + 1) until rule.right.size) {
                add(rule.right[j])
            }
        })

        val firstSet = firstSet(fakeRule, grammar)
        val noAtFirstSet = firstSet.filter { it.char != "@" }
        // 如果添加成功，说明有新的
        if (grammar.addFollow(symbol, noAtFirstSet.toSet())) {
            hasNew1 = true
        }

        // 第二种情况：first 有空
        if (firstSet.contains(Symbol("@"))) {
            if (grammar.addFollow(symbol, grammar.getFollow(rule.left))) {
                hasNew1 = true
            }
        }
    }
    return hasNew1
}