package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("""
        SELECT games.*, COUNT(plays.id) AS userPlayCount, AVG(plays.rating) AS userAvgRating 
        FROM games 
        LEFT JOIN plays ON games.objectId = plays.gameId 
        WHERE itemType != 'expansion' 
        GROUP BY games.objectId 
        ORDER BY objectName ASC
    """)
    fun getAllGamesWithStats(): Flow<List<GameWithStats>>

    @Query("SELECT * FROM games WHERE objectId = :id")
    suspend fun getGameById(id: Long): GameEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT COUNT(*) FROM games")
    suspend fun getGameCount(): Int
}

data class GameWithStats(
    @Embedded val game: GameEntity,
    val userPlayCount: Int,
    val userAvgRating: Float?
)
