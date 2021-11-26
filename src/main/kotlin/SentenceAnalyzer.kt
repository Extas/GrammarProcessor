import grammar.Grammar
import grammar.Rule
import grammar.Symbol
import grammar.process.generateFirst
import grammar.process.generateFollow
import grammar.process.removeLeftFactors
import grammar.process.removeLeftRecursion
import java.util.*

/**
 * @author Extas
 * @date 2021/11/25 18:20
 */
class SentenceAnalyzer {
    val analyzeLog: MutableList<String> = mutableListOf()
    val simplifyResults: MutableList<Rule> = mutableListOf()
    val removeLeftFactorsResults: MutableList<Rule> = mutableListOf()
    val removeLeftRecursionResults: MutableList<Rule> = mutableListOf()
    val follow: StringBuilder = StringBuilder()
    val first: StringBuilder = StringBuilder()


    fun analyze(grammarStr: String, sentence: String): Boolean {

        init()

        val grammar: Grammar = Grammar.fromString(grammarStr)

        grammarPreprocessing(grammar)

        val remainingChar: MutableList<Char> = sentence.toMutableList()
        val stack: Stack<Symbol> = Stack()
        stack.push(grammar.startSymbol)

        while (stack.isNotEmpty()) {

            val stackLog: StringBuilder = StringBuilder()
            stackLog.append("Stack: ")
            for (symbol in stack.reversed()) {
                stackLog.append(symbol.char)
                stackLog.append(" ")
            }

            val top = stack.pop()
            val char = try {
                remainingChar.removeFirst()
            } catch (e: NoSuchElementException) {
                '$'
            }

            if (top.isNonTerminal) {
                val rule = grammar.table.getRuleByNonTermAndTerm(top, Symbol(char))

                if (rule != null) {
                    for (symbol in rule.right.reversed()) {
                        stack.push(symbol)
                    }
                    remainingChar.add(0, char)
                    analyzeLog.add("$stackLog\t$rule\t$remainingChar \n")
                } else {
                    analyzeLog.add("Error")
                    return false
                }
            }

            if (top.isTerminal) {
                if (top.char[0] == char) {
                    analyzeLog.add("$stackLog\tMatch: $top\t$remainingChar \n")
                } else {
                    analyzeLog.add("Error")
                    return false
                }
            }
        }

        analyzeLog.add("Pass")
        return true
    }

    private fun init() {
        analyzeLog.clear()
        simplifyResults.clear()
        removeLeftFactorsResults.clear()
        removeLeftRecursionResults.clear()
        follow.clear()
        first.clear()
    }

    private fun grammarPreprocessing(grammar: Grammar) {

        grammar.simplifiedGrammar()
        simplifyResults.addAll(grammar.ruleManager.getAllRules())

        removeLeftFactors(grammar)
        removeLeftFactorsResults.addAll(grammar.ruleManager.getAllRules())

        removeLeftRecursion(grammar)
        removeLeftRecursionResults.addAll(grammar.ruleManager.getAllRules())

        generateFirst(grammar)
        generateFollow(grammar)

        for (rule in grammar.ruleManager.getAllRules()) {
            first.append(rule.firstToString())
        }

        for (symbol in grammar.nonterminalSymbols) {
            follow.append(symbol.followToString)
        }

        grammar.table.buildTable(grammar)
    }
}