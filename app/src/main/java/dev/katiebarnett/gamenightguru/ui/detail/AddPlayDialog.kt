package dev.katiebarnett.gamenightguru.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme

@Composable
fun AddPlayDialog(
    initialPlayTime: Int,
    onDismiss: () -> Unit,
    onConfirm: (numPlayers: Int, playTime: Int, rating: Float) -> Unit
) {
    var numPlayers by remember { mutableStateOf("2") }
    var playTime by remember { mutableStateOf(initialPlayTime.toString()) }
    var rating by remember { mutableFloatStateOf(7.0f) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log a Play") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = numPlayers,
                    onValueChange = { numPlayers = it },
                    label = { Text("Number of Players") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = playTime,
                    onValueChange = { playTime = it },
                    label = { Text("Play Time (minutes)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Column {
                    Text("Rating: %.1f".format(rating), style = MaterialTheme.typography.bodyMedium)
                    Slider(
                        value = rating,
                        onValueChange = { rating = it },
                        valueRange = 1f..10f,
                        steps = 17 // 1.0, 1.5, 2.0 ... 10.0
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        numPlayers.toIntOrNull() ?: 2,
                        playTime.toIntOrNull() ?: initialPlayTime,
                        rating
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddPlayDialogPreview() {
    GameNightGuruTheme {
        AddPlayDialog(
            initialPlayTime = 30,
            onDismiss = {},
            onConfirm = { _, _, _ -> }
        )
    }
}
