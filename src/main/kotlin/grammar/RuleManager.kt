package grammar

/**
 * @author Extas
 * @date 2021/11/23 20:47
 */
class RuleManager {
    private val rules = mutableSetOf<Rule>()

    private val ruleMap = mutableMapOf<Symbol, MutableSet<Rule>>()

    fun addRule(rule: Rule) {
        ruleMap.getOrPut(rule.left) { mutableSetOf() }.add(rule)
        rules.add(rule)
    }

    fun deleteRule(rule: Rule) {
        ruleMap.getOrPut(rule.left) { mutableSetOf() }.remove(rule)
        rules.remove(rule)
    }

    fun getRulesBySymbol(symbol: Symbol): MutableSet<Rule> {
        return ruleMap.getOrDefault(symbol, mutableSetOf())
    }

    fun getAllRules(): MutableSet<Rule> {
        return rules
    }
}