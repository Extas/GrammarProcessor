package ui

import WindowState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Extas
 * @date 2021/11/26 10:22
 */

@Composable
fun RemoveLeft(state: WindowState, modifier: Modifier) {
    val flag = state.flags
    val stateVertical1 = rememberScrollState(0)
    val stateVertical2 = rememberScrollState(0)
    Column(modifier = modifier.padding(2.dp)) {
        Text("Remove Left Factors Results")
        Card(
            modifier = Modifier.weight(1f).fillMaxSize().padding(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(Modifier.padding(2.dp).verticalScroll(stateVertical1)) {
                for (rule in state.analyzer.removeLeftFactorsResults) {
                    Text(rule.toString())
                }
            }
        }
        Text("Remove Left Recursion Results")
        Card(
            modifier = Modifier.weight(1f).fillMaxSize().padding(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Column(Modifier.padding(2.dp).verticalScroll(stateVertical2)) {
                for (rule in state.analyzer.removeLeftRecursionResults) {
                    Text(rule.toString())
                }
            }
        }
    }
}