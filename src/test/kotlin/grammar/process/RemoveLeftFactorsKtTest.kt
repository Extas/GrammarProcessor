package grammar.process

import grammar.Grammar
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/23 21:45
 */
internal class RemoveLeftFactorsKtTest {

    @Test
    fun removeLeftFactors() {
        val grammar = Grammar.fromString(
            "S -> d \n" +
                    " S -> aaB \n" +
                    " S -> aaaC \n" +
                    " S -> aaaDd \n" +
                    " S -> f \n"
        )
        removeLeftFactors(grammar)
    }
}