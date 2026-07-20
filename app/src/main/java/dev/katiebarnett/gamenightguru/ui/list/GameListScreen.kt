package dev.katiebarnett.gamenightguru.ui.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.ui.components.GameItem
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme

@Composable
fun GameListScreen(
    modifier: Modifier = Modifier,
    viewModel: GameListViewModel = hiltViewModel()
) {
    val games by viewModel.games.collectAsState()
    GameListContent(games = games, modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameListContent(
    games: List<GameEntity>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Game Night Guru") })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(games) { game ->
                GameItem(game = game)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameListScreenPreview() {
    GameNightGuruTheme {
        GameListContent(
            games = listOf(
                GameEntity(
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
                GameEntity(
                    objectId = 295947,
                    objectName = "Cascadia",
                    averageRating = 7.88,
                    numPlays = 10,
                    own = true,
                    forTrade = false,
                    want = false,
                    wantToBuy = false,
                    wantToPlay = true,
                    prevOwned = false,
                    preOrdered = false,
                    avgWeight = 1.84,
                    rank = 60,
                    minPlayers = 1,
                    maxPlayers = 4,
                    playingTime = 45,
                    maxPlayTime = 45,
                    minPlayTime = 30,
                    yearPublished = 2021,
                    bggRecPlayers = "1,2,3,4",
                    bggBestPlayers = "2,3",
                    bggRecAgeRange = "8+",
                    itemType = "standalone"
                )
            )
        )
    }
}
