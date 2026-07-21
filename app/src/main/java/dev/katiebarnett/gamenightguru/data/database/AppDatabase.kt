package dev.katiebarnett.gamenightguru.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GameEntity::class, PlayEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun playDao(): PlayDao
}
