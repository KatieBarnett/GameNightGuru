package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "plays",
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["objectId"],
            childColumns = ["gameId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("gameId")]
)
data class PlayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameId: Long,
    val numPlayers: Int,
    val playTime: Int,
    val rating: Float,
    val date: Long = System.currentTimeMillis()
)
