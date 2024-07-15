package authentication.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "app_preferences")
data class PreferenceEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Dao
interface DataPreferencesDao {
    @Query("SELECT value FROM app_preferences WHERE key = :key")
    fun getValue(key: String): Flow<String?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: PreferenceEntity)
}

interface DB {
    fun clearAllTables()
}

@Database(entities = [PreferenceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun getDao(): DataPreferencesDao
    override fun clearAllTables() {}
}