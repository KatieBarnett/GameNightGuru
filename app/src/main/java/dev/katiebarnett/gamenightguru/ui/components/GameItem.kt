package dev.katiebarnett.gamenightguru.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme

@Composable
fun GameItem(game: GameEntity, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${game.objectName} (${game.yearPublished})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Rating: %.2f".format(game.averageRating),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Weight: %.2f".format(game.avgWeight),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Rank: ${game.rank}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Players: ${game.minPlayers}-${game.maxPlayers}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Play Time: ${game.minPlayTime}-${game.maxPlayTime} mins",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Age: ${game.bggRecAgeRange}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    GameNightGuruTheme {
        GameItem(
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
            )
        )
    }
}
