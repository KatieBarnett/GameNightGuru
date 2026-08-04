package dev.katiebarnett.gamenightguru

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appfunctions.AppFunction
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

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
    @AppFunction(isDescribedByKDoc = true)
    suspend fun queryGames(
        maxDurationMinutes: Int? = null,
        numPlayers: Int? = null,
        minAge: Int? = null
    ): List<GameSummary> = withContext(Dispatchers.IO) {
        val allGames = gameRepository.getAllGames().first()
        allGames.filter { game ->
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
    @AppFunction(isDescribedByKDoc = true)
    suspend fun addPlay(
        gameId: Long,
        numPlayers: Int,
        playTimeMinutes: Int,
        rating: Float
    ): PlayRecord = withContext(Dispatchers.IO) {
        val game = gameRepository.getGameById(gameId)
            ?: throw AppFunctionElementNotFoundException("Game with ID $gameId not found")

        val play = PlayEntity(
            gameId = gameId,
            numPlayers = numPlayers,
            playTime = playTimeMinutes,
            rating = rating
        )
        gameRepository.insertPlay(play)

        PlayRecord(
            gameName = game.objectName,
            numPlayers = numPlayers,
            playTimeMinutes = playTimeMinutes,
            rating = rating
        )
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


