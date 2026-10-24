package edu.iau.cshj.csc402.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.iau.cshj.csc402.myapplication.ui.theme.Cream
import edu.iau.cshj.csc402.myapplication.ui.theme.JeelTheme

@Composable
internal fun PreviewScreen(content: @Composable ColumnScope.() -> Unit) {
    JeelTheme {
        Surface(color = Cream) {
            Column(
                Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                content = content
            )
        }
    }
}
