package ui

import WindowState
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @author Extas
 * @date 2021/11/26 10:19
 */

@Composable
fun Grammar(state: WindowState, modifier: Modifier) {
    OutlinedTextField(
        value = state.grammarStr,
        onValueChange = { state.grammarStr = it },
        label = { Text("Grammar") },
        modifier = modifier.fillMaxHeight()
    )
}