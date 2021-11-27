import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.*
import kotlinx.coroutines.launch
import ui.RowInfo
import java.awt.FileDialog
import java.io.File
import java.nio.file.Path

fun main() = application {
    val windowState = remember { WindowState(Path.of("")) }
    Window(onCloseRequest = ::exitApplication) {
        WindowMenuBar(windowState)
        App(windowState)

        if (windowState.openDialog.isAwaiting) {
            FileDialog(
                title = "选择文法源文件",
                isLoad = true,
                onResult = {
                    windowState.openDialog.onResult(it)
                }
            )
        }
    }
}

@Composable
@Preview
fun App(windowState: WindowState) {

    MaterialTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            RowInfo(windowState)
            Sentence(windowState)
        }
    }
}

@Composable
private fun FrameWindowScope.WindowMenuBar(state: WindowState) = MenuBar {
    val scope = rememberCoroutineScope()

    fun open() = scope.launch { state.open() }

    Menu("文件") {
        Item("打开...", onClick = { open() })
        Separator()
    }
}

@Composable
fun FrameWindowScope.FileDialog(
    title: String,
    isLoad: Boolean,
    onResult: (result: Path?) -> Unit,
) = AwtWindow(
    create = {
        object : FileDialog(window, "Choose a file", if (isLoad) LOAD else SAVE) {
            override fun setVisible(value: Boolean) {
                super.setVisible(value)
                if (value) {
                    if (file != null) {
                        onResult(File(directory).resolve(file).toPath())
                    } else {
                        onResult(null)
                    }
                }
            }
        }.apply {
            this.title = title
        }
    },
    dispose = FileDialog::dispose
)

