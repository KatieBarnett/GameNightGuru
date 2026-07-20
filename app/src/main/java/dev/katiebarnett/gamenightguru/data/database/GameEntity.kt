package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val objectId: Long,
    val objectName: String,
    val averageRating: Double,
    val numPlays: Int,
    val own: Boolean,
    val forTrade: Boolean,
    val want: Boolean,
    val wantToBuy: Boolean,
    val wantToPlay: Boolean,
    val prevOwned: Boolean,
    val preOrdered: Boolean,
    val avgWeight: Double,
    val rank: Int,
    val minPlayers: Int,
    val maxPlayers: Int,
    val playingTime: Int,
    val maxPlayTime: Int,
    val minPlayTime: Int,
    val yearPublished: Int,
    val bggRecPlayers: String,
    val bggBestPlayers: String,
    val bggRecAgeRange: String,
    val itemType: String
)
