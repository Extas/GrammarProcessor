import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * @author Extas
 * @date 2021/11/26 8:57
 */
internal class sentenceAnalyzerTest {

    @Test
    fun analyze() {
        val analyzer = SentenceAnalyzer()
        val grammar =
            """
                S -> aaaT
                S -> aaT
                S -> b
                T -> c
                T -> Td
            """.trimIndent()
//            "S -> aT \n" +
//                    " T -> bc \n" +
//                    // 有害规则
//                    " U -> V \n" +
//                    " V -> U \n" +
//                    // 不可到达规则
//                    " U -> V \n" +
//                    " V -> v \n" +
//                    // 不可终结规则
//                    " T -> C \n" +
//                    " C -> B \n" +
//                    " B -> A \n" +
//                    " A -> C"
        assertTrue(analyzer.analyze(grammar, "aaac"))
        println(analyzer.analyzeLog.toString())

        assertFalse(analyzer.analyze(grammar, "anc"))
        println(analyzer.analyzeLog.toString())
    }
}