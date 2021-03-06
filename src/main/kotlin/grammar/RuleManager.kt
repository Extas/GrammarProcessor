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

    fun sort(startSymbol: Symbol) {
        val startRule = rules.filter { it.left == startSymbol }
        val list = rules.toMutableList()
        for (rule in startRule) {
            list.remove(rule)
            list.add(0, rule)
        }
        rules.clear()
        rules.addAll(list)
    }

    fun addRules(rules: Collection<Rule>) {
        rules.forEach { addRule(it) }
    }

    fun deleteRule(rule: Rule) {
        ruleMap.getOrPut(rule.left) { mutableSetOf() }.remove(rule)
        rules.remove(rule)
    }

    fun removeRuleBySymbol(symbol: Symbol) {
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
        return mutableSetOf<Rule>().apply { addAll(ruleMap.getOrDefault(symbol, mutableSetOf())) }
    }

    fun getAllRules(): MutableSet<Rule> {
        return mutableSetOf<Rule>().apply { addAll(rules) }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (rule in rules) {
            stringBuilder.append(rule.toString()).append("\n")
        }
        return stringBuilder.toString()
    }
}