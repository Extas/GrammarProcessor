import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Sentence(state: WindowState) {

    Row(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(8.dp)) {
        TextField(
            value = state.sentence,
            onValueChange = { state.sentence = it },
            label = { Text("Sentence") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(0.86f)
        )
        Button(
            onClick = { state.analyze() },
            modifier = Modifier.padding(start = 10.dp).fillMaxHeight()
        ) {
            Text("Analyze")
        }
    }
}