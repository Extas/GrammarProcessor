import grammar.Grammar
import grammar.Rule
import grammar.Symbol
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/11 16:51
 */
internal class GrammarTest {

    @Test
    fun getStartSymbol() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc")
        assertEquals(Symbol("S"), grammar.startSymbol)
    }

    @Test
    fun getNonterminalSymbols() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc")
        assertEquals(mutableSetOf(Symbol("S"), Symbol("T")), grammar.nonterminalSymbols)
    }

    @Test
    fun getTerminalSymbols() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc")
        assertEquals(mutableSetOf(Symbol("a"), Symbol("b"), Symbol("c")), grammar.terminalSymbols)
    }

    @Test
    fun getRules() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc")
        assertEquals(
            mutableSetOf(
                Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c")))
            ), grammar.getRules()
        )
    }

    @Test
    fun removeHarmfulRules() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc \n U -> U \n U -> T")
        grammar.removeHarmfulRules()
        assertEquals(
            mutableSetOf(
                Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c"))),
                Rule(Symbol("U"), listOf(Symbol("T")))
            ), grammar.getRules()
        )
    }

    @Test
    fun removeUselessSymbols() {
        val grammar = Grammar.fromString(
            "S -> aT \n" +
                    " T -> bc \n" +
                    // 有害规则
                    " U -> U \n" +
                    // 不可到达规则
                    " U -> V \n" +
                    " V -> v"
        )
        grammar.removeHarmfulRules()
        grammar.removeUselessSymbols()
        assertEquals(
            mutableSetOf(
                Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c"))),
            ), grammar.getRules()
        )
    }

    @Test
    fun removeNonterminalRules() {
        val grammar = Grammar.fromString(
            "S -> aT \n" +
                    " T -> bc \n" +
                    // 有害规则
                    " U -> U \n" +
                    // 不可到达规则
                    " U -> V \n" +
                    " V -> v \n" +
                    // 不可终结规则
                    " T -> C \n" +
                    " C -> B \n" +
                    " B -> A \n" +
                    " A -> C"
        )
        grammar.simplifiedGrammar()
        assertEquals(
            mutableSetOf(
                Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c"))),
            ), grammar.getRules()
        )
    }


}