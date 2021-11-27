import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path

/**
 * @author Extas
 * @date 2021/11/26 9:21
 */
class WindowState(path: Path?) {
    fun analyze() {
        analyzer.analyze(grammarStr, sentence)
        grammarStr = analyzer.simplifyResults
        flags = !flags
    }

    var grammarStr by mutableStateOf(
        "S -> b \n" +
                " S -> aaT \n" +
                " S -> aaaT \n" +
                " T -> Td \n" +
                " T -> c \n" +
                // 有害规则
                " U -> V \n" +
                " V -> U \n" +
                // 不可到达规则
                " U -> V \n" +
                " V -> v \n" +
                // 不可终结规则
                " T -> C \n" +
                " C -> B \n" +
                " B -> A \n" +
                " A -> C"
    )
    var sentence by mutableStateOf("aacdd")
    val analyzer = SentenceAnalyzer()
    var flags by mutableStateOf(false)

    val openDialog = DialogState<Path?>()
    var path by mutableStateOf(path)
        private set

    suspend fun open() {
        val path = openDialog.awaitResult()
        if (path != null) {
            open(path)
        }
    }

    private suspend fun open(path: Path) {
        this.path = path
        try {
            grammarStr = path.readTextAsync()
        } catch (e: Exception) {
            e.printStackTrace()
            grammarStr = "Cannot read $path"
        }
    }

    private suspend fun Path.readTextAsync() = withContext(Dispatchers.IO) {
        toFile().readText()
    }

    class DialogState<T> {
        private var onResult: CompletableDeferred<T>? by mutableStateOf(null)

        val isAwaiting get() = onResult != null

        suspend fun awaitResult(): T {
            onResult = CompletableDeferred()
            val result = onResult!!.await()
            onResult = null
            return result
        }

        fun onResult(result: T) = onResult!!.complete(result)
    }
}