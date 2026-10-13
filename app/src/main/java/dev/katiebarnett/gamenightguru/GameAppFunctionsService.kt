package dev.katiebarnett.gamenightguru

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appfunctions.AppFunction
import androidx.appfunctions.AppFunctionDeclaration
import androidx.appfunctions.AppFunctionElementNotFoundException
import androidx.appfunctions.AppFunctionSerializable
import androidx.appfunctions.AppFunctionService
import androidx.appfunctions.AppFunctionServiceEntryPoint
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import dev.katiebarnett.gamenightguru.data.database.PlayEntity
import dev.katiebarnett.gamenightguru.data.repository.GameRepository
import kotlinx.coroutines.flow.first

/**
 * This app helps you manage your board game collection and log game plays.
 *
 * Operational Patterns:
 * - Use 'queryGames' to find the correct 'gameId' before calling 'addPlay'.
 * - When searching for games, you can provide duration, number of players, and age filters.
 */
@RequiresApi(Build.VERSION_CODES.BAKLAVA)
@AppFunctionServiceEntryPoint(
    serviceName = "GameAppFunctionsService",
    appFunctionXmlFileName = "game_app_functions"
)
abstract class GameAppFunctions : AppFunctionService() {

    private lateinit var gameRepository: GameRepository

    override fun onCreate() {
        super.onCreate()
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            GameAppFunctionsEntryPoint::class.java
        )
        gameRepository = entryPoint.gameRepository()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GameAppFunctionsEntryPoint {
        fun gameRepository(): GameRepository
    }

    /**
     * Query what game to play based on duration, number of players, and age.
     *
     * @param maxDurationMinutes The maximum duration in minutes for the game.
     * @param numPlayers The number of players.
     * @param minAge The minimum age of the players.
     * @return A list of games matching the criteria.
     */
    @AppFunctionDeclaration(isDescribedByKDoc = true)
    suspend fun queryGames(
        maxDurationMinutes: Int? = null,
        numPlayers: Int? = null,
        minAge: Int? = null
    ): List<GameSummary> {
        val allGames = gameRepository.getAllGames().first()
        return allGames.filter { game ->
            val durationMatch = maxDurationMinutes == null || game.maxPlayTime <= maxDurationMinutes
            val playersMatch = numPlayers == null || (numPlayers >= game.minPlayers && numPlayers <= game.maxPlayers)
            val ageMatch = minAge == null || extractMinAge(game.bggRecAgeRange) <= minAge
            durationMatch && playersMatch && ageMatch
        }.map { game ->
            GameSummary(
                id = game.objectId,
                name = game.objectName,
                minPlayers = game.minPlayers,
                maxPlayers = game.maxPlayers,
                playingTime = game.playingTime
            )
        }
    }

    /**
     * Add a play of a specific game.
     * Required workflow: Call [queryGames] first to find the correct [gameId].
     *
     * @param gameId The unique identifier of the game.
     * @param numPlayers The number of players for this specific play.
     * @param playTimeMinutes The duration of the play in minutes.
     * @param rating The rating for this play (1-10).
     * @return Confirmation of the added play.
     */
    @AppFunctionDeclaration(isDescribedByKDoc = true)
    suspend fun addPlay(
        gameId: Long,
        numPlayers: Int,
        playTimeMinutes: Int,
        rating: Float
    ): PlayRecord {
        val game = gameRepository.getGameById(gameId)
            ?: throw AppFunctionElementNotFoundException("Game with ID $gameId not found")

        val play = PlayEntity(
            gameId = gameId,
            numPlayers = numPlayers,
            playTime = playTimeMinutes,
            rating = rating
        )
        gameRepository.insertPlay(play)

        return PlayRecord(
            gameName = game.objectName,
            numPlayers = numPlayers,
            playTimeMinutes = playTimeMinutes,
            rating = rating
        )
    }

    /**
     * Delete the most recent play of a specific game.
     * Required workflow: Call [queryGames] first to find the correct [gameId].
     * **Constraint:** Before deleting a play, you MUST explicitly ask the user for confirmation. Show the game name, the date, and the play details (rating, numPlayers, playTime) and ask "Are you sure you want to delete this play?".
     *
     * @param gameId The unique identifier of the game.
     * @return Confirmation of the deleted play or a message if no plays were found.
     */
    @AppFunctionDeclaration(isDescribedByKDoc = true)
    suspend fun deleteLatestPlay(
        gameId: Long
    ): String {
        val game = gameRepository.getGameById(gameId)
            ?: throw AppFunctionElementNotFoundException("Game with ID $gameId not found")

        val plays = gameRepository.getPlaysForGame(gameId).first()
        if (plays.isEmpty()) {
            return "No plays found for game ${game.objectName}."
        }

        val playToDelete = plays.first() // Assuming getPlaysForGame orders by date/id descending
        gameRepository.deletePlay(playToDelete)

        return "Successfully deleted the most recent play for ${game.objectName}."
    }

    /**
     * Provide a way to open the game's plays in the app.
     * Required workflow: Call [queryGames] first to find the correct [gameId].
     * **Constraint:** You MUST present the returned `openPlaysAction` to the user as a button so they can open the app and view their plays.
     *
     * @param gameId The unique identifier of the game.
     * @return A button to open the Game Night Guru app with a pending intent
     */
    @AppFunctionDeclaration(isDescribedByKDoc = true)
    suspend fun getPlays(
        gameId: Long
    ): PendingIntent {
        val intent = Intent(this, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW
            putExtra("gameId", gameId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            gameId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return pendingIntent
    }

    private fun extractMinAge(ageRange: String): Int {
        return ageRange.filter { it.isDigit() }.toIntOrNull() ?: 0
    }
}

/**
 * A summary of a game.
 */
@AppFunctionSerializable(isDescribedByKDoc = true)
data class GameSummary(
    /** The unique identifier of the game */
    val id: Long,
    /** The name of the game */
    val name: String,
    /** The minimum number of players */
    val minPlayers: Int,
    /** The maximum number of players */
    val maxPlayers: Int,
    /** The average playing time in minutes */
    val playingTime: Int
)

/**
 * A record of a game play.
 */
@AppFunctionSerializable(isDescribedByKDoc = true)
data class PlayRecord(
    /** The name of the game played */
    val gameName: String,
    /** The number of players in this play */
    val numPlayers: Int,
    /** The duration of the play in minutes */
    val playTimeMinutes: Int,
    /** The rating given to this play */
    val rating: Float
)

/**
 * The result of querying plays for a game.
 */
@AppFunctionSerializable(isDescribedByKDoc = true)
data class GamePlaysResult(
    /** The name of the game */
    val gameName: String,
    /** The list of plays for this game */
    val plays: List<PlayRecord>,
    /** Action to open the app and view the plays. Present this action to the user as a UI element so they can click it. */
    val openPlaysAction: PendingIntent
)
