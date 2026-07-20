package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}
