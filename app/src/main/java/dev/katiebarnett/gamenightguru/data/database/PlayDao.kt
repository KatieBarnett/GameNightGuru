package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayDao {
    @Transaction
    @Query("SELECT * FROM plays ORDER BY date DESC")
    fun getAllPlays(): Flow<List<PlayWithGame>>

    @Query("SELECT * FROM plays WHERE gameId = :gameId ORDER BY id DESC")
    fun getPlaysForGame(gameId: Long): Flow<List<PlayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlay(play: PlayEntity)

    @Delete
    suspend fun deletePlay(play: PlayEntity)
}
