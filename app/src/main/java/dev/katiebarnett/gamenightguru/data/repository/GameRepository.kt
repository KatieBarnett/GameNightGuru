package dev.katiebarnett.gamenightguru.data.repository

import dev.katiebarnett.gamenightguru.data.database.GameDao
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.data.importer.DataImporter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

interface GameRepository {
    fun getAllGames(): Flow<List<GameEntity>>
    suspend fun insertGames(games: List<GameEntity>)
}

@Singleton
class GameRepositoryImpl @Inject constructor(
    private val gameDao: GameDao,
    private val dataImporter: DataImporter
) : GameRepository {
    override fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames().onStart {
        if (gameDao.getGameCount() == 0) {
            val games = dataImporter.importFromCsv("collection.csv")
            gameDao.insertGames(games)
        }
    }
    
    override suspend fun insertGames(games: List<GameEntity>) = gameDao.insertGames(games)
}
