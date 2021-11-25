package grammar.process

import grammar.Grammar
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/25 16:14
 */
internal class FollowKtTest {

    @Test
    fun follow() {
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
        generateFollow(grammar)
        for (symbol in grammar.nonterminalSymbols) {
            println(symbol.followToString)
        }
    }
}