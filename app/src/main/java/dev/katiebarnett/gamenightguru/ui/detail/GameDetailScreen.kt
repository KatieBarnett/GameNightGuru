package dev.katiebarnett.gamenightguru.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.data.database.PlayEntity
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GameDetailViewModel = hiltViewModel<GameDetailViewModel, GameDetailViewModel.Factory>(
        creationCallback = { factory -> factory.create(gameId) }
    )
) {
    val game by viewModel.game.collectAsState()
    val plays by viewModel.plays.collectAsState()
    
    GameDetailContent(
        game = game,
        plays = plays,
        onBack = onBack,
        onAddPlay = viewModel::addPlay,
        onDeletePlay = viewModel::deletePlay,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailContent(
    game: GameEntity?,
    plays: List<PlayEntity>,
    onBack: () -> Unit,
    onAddPlay: (Int, Int, Float) -> Unit,
    onDeletePlay: (PlayEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddPlayDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(game?.objectName ?: "Game Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddPlayDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Play")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (plays.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No plays recorded yet", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(plays) { play ->
                        PlayItem(play = play, onDelete = { onDeletePlay(play) })
                    }
                }
            }
        }
    }

    if (showAddPlayDialog) {
        AddPlayDialog(
            initialPlayTime = game?.playingTime ?: 30,
            onDismiss = { showAddPlayDialog = false },
            onConfirm = { numPlayers, playTime, rating ->
                onAddPlay(numPlayers, playTime, rating)
                showAddPlayDialog = false
            }
        )
    }
}


@Composable
fun PlayItem(play: PlayEntity, onDelete: () -> Unit) {
    val dateFormatter = remember {
        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
            .withZone(ZoneId.systemDefault())
    }
    val dateString = remember(play.date) {
        dateFormatter.format(Instant.ofEpochMilli(play.date))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${play.numPlayers} players • ${play.playTime} min",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Rating: %.1f".format(play.rating),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Play", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    GameNightGuruTheme {
        GameDetailContent(
            game = GameEntity(
                objectId = 173346,
                objectName = "7 Wonders Duel",
                averageRating = 8.07,
                numPlays = 5,
                own = true,
                forTrade = false,
                want = false,
                wantToBuy = false,
                wantToPlay = true,
                prevOwned = false,
                preOrdered = false,
                avgWeight = 2.22,
                rank = 24,
                minPlayers = 2,
                maxPlayers = 2,
                playingTime = 30,
                maxPlayTime = 30,
                minPlayTime = 30,
                yearPublished = 2015,
                bggRecPlayers = "2",
                bggBestPlayers = "2",
                bggRecAgeRange = "10+",
                itemType = "standalone"
            ),
            plays = listOf(
                PlayEntity(id = 1, gameId = 173346, numPlayers = 2, playTime = 35, rating = 9.0f),
                PlayEntity(id = 2, gameId = 173346, numPlayers = 2, playTime = 30, rating = 8.5f)
            ),
            onBack = {},
            onAddPlay = { _, _, _ -> },
            onDeletePlay = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayItemPreview() {
    GameNightGuruTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            PlayItem(
                play = PlayEntity(id = 1, gameId = 173346, numPlayers = 2, playTime = 35, rating = 9.0f),
                onDelete = {}
            )
        }
    }
}
