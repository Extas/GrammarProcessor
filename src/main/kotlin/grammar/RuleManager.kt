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

    fun addRules(rules: Collection<Rule>) {
        rules.forEach { addRule(it) }
    }

    fun deleteRule(rule: Rule) {
        ruleMap.getOrPut(rule.left) { mutableSetOf() }.remove(rule)
        rules.remove(rule)
    }

    fun deleteRuleBySymbol(symbol: Symbol) {
        val toRemoveRules = ruleMap.getOrDefault(symbol, mutableSetOf())
        for (rule in toRemoveRules) {
            deleteRule(rule)
        }
    }

    fun deleteAllRules() {
        rules.clear()
        ruleMap.clear()
    }

    fun getRulesBySymbol(symbol: Symbol): MutableSet<Rule> {
        return ruleMap.getOrDefault(symbol, mutableSetOf())
    }

    fun getAllRules(): MutableSet<Rule> {
        return mutableSetOf<Rule>().apply { addAll(rules) }
    }
}