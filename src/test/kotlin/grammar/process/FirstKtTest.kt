package grammar.process

import grammar.Grammar
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/25 14:01
 */
internal class FirstKtTest {

    @Test
    fun firstSet() {
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
        generateFirst(grammar)
        for (rule in grammar.ruleManager.getAllRules()) {
            rule.printFirst()
        }
    }
}