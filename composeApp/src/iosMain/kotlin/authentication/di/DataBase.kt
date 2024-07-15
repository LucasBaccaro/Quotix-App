package authentication.di

import androidx.room.Room
import androidx.room.RoomDatabase
import authentication.data.local.AppDatabase
import authentication.data.local.instantiateImpl
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFilePath = NSHomeDirectory() + "/app.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
}