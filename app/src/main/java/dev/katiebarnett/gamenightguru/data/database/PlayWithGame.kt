package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Embedded
import androidx.room.Relation

data class PlayWithGame(
    @Embedded val play: PlayEntity,
    @Relation(
        parentColumn = "gameId",
        entityColumn = "objectId"
    )
    val game: GameEntity
)