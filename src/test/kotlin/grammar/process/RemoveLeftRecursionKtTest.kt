package grammar.process

import grammar.Grammar
import grammar.Rule
import grammar.Symbol
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/25 10:40
 */
internal class RemoveLeftRecursionKtTest {

    @Test
    fun removeLeftRecursion() {
        val grammar = Grammar.fromString(
            "S -> aT \n" +
                    // 递归规则
                    " T -> Tc \n" +
                    " T -> b \n"
        )
        removeLeftRecursion(grammar)
        assertEquals(
            mutableSetOf(
                Rule(Symbol("S"), mutableListOf(Symbol("a"), Symbol("T"))),
                Rule(Symbol("T'"), mutableListOf(Symbol("c"), Symbol("T'"))),
                Rule(Symbol("T"), mutableListOf(Symbol("b"), Symbol("T'"))),
                Rule(Symbol("T'"), mutableListOf(Symbol("$"))),
            ), grammar.ruleManager.getAllRules()
        )
    }

    @Test
    fun removeLeftRecursion2() {
        val grammar = Grammar.fromString(
            "S -> Ab \n" +
                    "S -> a \n" +
                    " A -> Bc \n" +
                    " A -> t \n" +
                    " B -> Sb \n" +
                    " B -> l \n" +
                    " B -> d \n"
        )
        removeLeftRecursion(grammar)
        println(grammar.ruleManager.toString())
        println(
            "               S->lcbs'|dcbs'|tbs'|as'               \n" +
                    "               s'->bcbs'|ε               \n" +
                    "               A->Sbc|lc|dc|t               \n" +
                    "               B->Sb|l|d "
        )
    }
}