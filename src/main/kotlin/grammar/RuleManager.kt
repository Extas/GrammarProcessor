package grammar

/**
 * @author Extas
 * @date 2021/11/23 20:47
 */
class RuleManager {
    private val rules = mutableSetOf<Rule>()

    fun addRule(rule: Rule) {
        rules.add(rule)
    }

    fun deleteRule(rule: Rule) {
        rules.remove(rule)
    }

    fun getRules(): MutableSet<Rule> {
        return rules
    }
}