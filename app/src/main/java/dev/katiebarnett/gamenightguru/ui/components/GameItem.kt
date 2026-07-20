package dev.katiebarnett.gamenightguru.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme

@Composable
fun GameItem(game: GameEntity, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        // Fun colorful stripe at the top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(MaterialTheme.colorScheme.secondary)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = game.objectName,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = game.yearPublished.toString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                RatingBadge(rating = game.averageRating)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoBadge(
                    icon = Icons.Default.People,
                    text = if (game.minPlayers == game.maxPlayers) game.minPlayers.toString() else "${game.minPlayers}-${game.maxPlayers}",
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
                InfoBadge(
                    icon = Icons.Default.Schedule,
                    text = "${game.playingTime}m",
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
                InfoBadge(
                    icon = Icons.Default.ChildCare,
                    text = game.bggRecAgeRange,
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoBadge(
                    icon = Icons.Default.FitnessCenter,
                    text = "Weight: %.1f".format(game.avgWeight),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
                InfoBadge(
                    icon = Icons.Default.EmojiEvents,
                    text = "Rank: #${game.rank}",
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
fun RatingBadge(rating: Double, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onTertiary
            )
            Text(
                text = "%.1f".format(rating),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Composable
fun InfoBadge(
    icon: ImageVector,
    text: String,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = containerColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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
