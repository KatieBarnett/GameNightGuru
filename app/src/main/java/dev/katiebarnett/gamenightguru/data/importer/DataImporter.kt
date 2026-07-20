package dev.katiebarnett.gamenightguru.data.importer

import android.content.Context
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import java.io.InputStream
import javax.inject.Inject

class DataImporter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun importFromCsv(fileName: String): List<GameEntity> {
        val inputStream: InputStream = context.assets.open(fileName)
        return csvReader().readAllWithHeader(inputStream).map { row ->
            GameEntity(
                objectId = row["objectid"]?.toLongOrNull() ?: 0L,
                objectName = row["objectname"] ?: "",
                averageRating = row["average"]?.toDoubleOrNull() ?: 0.0,
                numPlays = row["numplays"]?.toIntOrNull() ?: 0,
                own = row["own"] == "1",
                forTrade = row["fortrade"] == "1",
                want = row["want"] == "1",
                wantToBuy = row["wanttobuy"] == "1",
                wantToPlay = row["wanttoplay"] == "1",
                prevOwned = row["prevowned"] == "1",
                preOrdered = row["preordered"] == "1",
                avgWeight = row["avgweight"]?.toDoubleOrNull() ?: 0.0,
                rank = row["rank"]?.toIntOrNull() ?: 0,
                minPlayers = row["minplayers"]?.toIntOrNull() ?: 0,
                maxPlayers = row["maxplayers"]?.toIntOrNull() ?: 0,
                playingTime = row["playingtime"]?.toIntOrNull() ?: 0,
                maxPlayTime = row["maxplaytime"]?.toIntOrNull() ?: 0,
                minPlayTime = row["minplaytime"]?.toIntOrNull() ?: 0,
                yearPublished = row["yearpublished"]?.toIntOrNull() ?: 0,
                bggRecPlayers = row["bggrecplayers"] ?: "",
                bggBestPlayers = row["bggbestplayers"] ?: "",
                bggRecAgeRange = row["bggrecagerange"] ?: "",
                itemType = row["itemtype"] ?: ""
            )
        }
    }
}
