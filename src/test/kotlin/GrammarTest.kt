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
                Grammar.Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Grammar.Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c")))
            ), grammar.rules
        )
    }

    @Test
    fun removeHarmfulRules() {
        val grammar = Grammar.fromString("S -> aT \n T -> bc \n U -> U \n U -> T")
        grammar.removeHarmfulRules()
        assertEquals(
            mutableSetOf(
                Grammar.Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Grammar.Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c"))),
                Grammar.Rule(Symbol("U"), listOf(Symbol("T")))
            ), grammar.rules
        )
    }

    @Test
    fun removeUselessSymbols() {
        val grammar = Grammar.fromString(
            "S -> aT \n" +
                    " T -> bc \n" +
                    " U -> U \n" +
                    " U -> V \n" +
                    " V -> v"
        )
        grammar.removeHarmfulRules()
        grammar.removeUselessSymbols()
        assertEquals(
            mutableSetOf(
                Grammar.Rule(Symbol("S"), listOf(Symbol("a"), Symbol("T"))),
                Grammar.Rule(Symbol("T"), listOf(Symbol("b"), Symbol("c"))),
            ), grammar.rules
        )
    }


}