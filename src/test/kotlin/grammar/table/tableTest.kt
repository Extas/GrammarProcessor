package grammar.table

import grammar.Grammar
import grammar.process.generateFirst
import grammar.process.generateFollow
import grammar.process.removeLeftFactors
import grammar.process.removeLeftRecursion
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/25 17:15
 */
internal class tableTest {

    @Test
    fun buildTable() {
        val grammar = Grammar.fromString(
            "S -> AB\n" +
                    "S -> bC\n" +
                    "A -> @\n" +
                    "A -> b\n" +
                    "B -> @\n" +
                    "B -> aD\n" +
                    "C -> AD\n" +
                    "C -> b\n" +
                    "D -> aS\n" +
                    "D -> c"
        )
        grammar.simplifiedGrammar()
        removeLeftFactors(grammar)
        removeLeftRecursion(grammar)
        generateFirst(grammar)
        generateFollow(grammar)

        try {
            grammar.table.buildTable(grammar)
        } catch (e: Exception) {
            println(e.message)
        }

        for (rule in grammar.ruleManager.getAllRules()) {
            rule.firstToString()
        }
        for (symbol in grammar.nonterminalSymbols) {
            println(grammar.getFollowToString(symbol))
        }
        println(grammar.table.toString())
    }
}