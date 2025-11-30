package com.example.bookinterfacecreator.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun BookInterfaceCreatorThemePreview() {
    BookInterfaceCreatorTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Title sample", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Text("Subtitle", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Text("Chip", modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.small)
                        .padding(horizontal = 12.dp, vertical = 6.dp), color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Surface variant box
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}

