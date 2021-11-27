package ui

import WindowState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author Extas
 * @date 2021/11/26 10:18
 */

@Composable
fun RowInfo(windowState: WindowState) {
    Row(modifier = Modifier.padding(8.dp).fillMaxWidth().fillMaxHeight(0.86f)) {
        Grammar(windowState, Modifier.weight(0.5f).fillMaxHeight())
        RemoveLeft(windowState, Modifier.weight(1f).fillMaxHeight())
        FirstAndFollow(windowState, Modifier.weight(1f).fillMaxHeight())
        Log(windowState, Modifier.weight(1.5f).fillMaxHeight())
    }
}