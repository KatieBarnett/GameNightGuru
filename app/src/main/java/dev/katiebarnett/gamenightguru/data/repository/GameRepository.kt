package dev.katiebarnett.gamenightguru.data.repository

import dev.katiebarnett.gamenightguru.data.database.GameDao
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.data.database.GameWithStats
import dev.katiebarnett.gamenightguru.data.database.PlayDao
import dev.katiebarnett.gamenightguru.data.database.PlayEntity
import dev.katiebarnett.gamenightguru.data.database.PlayWithGame
import dev.katiebarnett.gamenightguru.data.importer.DataImporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface GameRepository {
    fun getAllGames(): Flow<List<GameEntity>>
    fun getAllGamesWithStats(): Flow<List<GameWithStats>>
    suspend fun getGameById(gameId: Long): GameEntity?
    suspend fun insertGames(games: List<GameEntity>)
    
    // Play methods
    fun getAllPlays(): Flow<List<PlayWithGame>>
    fun getPlaysForGame(gameId: Long): Flow<List<PlayEntity>>
    suspend fun insertPlay(play: PlayEntity)
    suspend fun deletePlay(play: PlayEntity)
}

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val gameDao: GameDao,
    private val playDao: PlayDao,
    private val dataImporter: DataImporter
) : GameRepository {
    override fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGamesWithStats().onStart {
        if (gameDao.getGameCount() == 0) {
            val games = withContext(Dispatchers.IO) {
                dataImporter.importFromCsv("collection.csv")
            }
            gameDao.insertGames(games)
        }
    }.map { it.map { stats -> stats.game } }

    override fun getAllGamesWithStats(): Flow<List<GameWithStats>> = gameDao.getAllGamesWithStats().onStart {
        if (gameDao.getGameCount() == 0) {
            val games = withContext(Dispatchers.IO) {
                dataImporter.importFromCsv("collection.csv")
            }
            gameDao.insertGames(games)
        }
    }

    override suspend fun getGameById(gameId: Long): GameEntity? = gameDao.getGameById(gameId)
    
    override suspend fun insertGames(games: List<GameEntity>) = gameDao.insertGames(games)

    override fun getAllPlays(): Flow<List<PlayWithGame>> = playDao.getAllPlays()

    override fun getPlaysForGame(gameId: Long): Flow<List<PlayEntity>> = playDao.getPlaysForGame(gameId)

    override suspend fun insertPlay(play: PlayEntity) = playDao.insertPlay(play)

    override suspend fun deletePlay(play: PlayEntity) = playDao.deletePlay(play)
}
