package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val birthDate: String, // "yyyy-MM-dd"
    val birthTime: String = "",
    val birthCity: String = ""
)

@Entity(tableName = "daily_tarot")
data class DailyTarotEntity(
    @PrimaryKey val dateString: String, // "yyyy-MM-dd"
    val cardId: Int,
    val isReversed: Boolean,
    val userReflection: String = ""
)

@Entity(tableName = "saved_readings")
data class SavedReadingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val spreadType: String,
    val question: String,
    val cardData: String, // serialized string of id:reversed,id:reversed
    val aiInterpretation: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val sender: String, // "USER" or "ORACLE"
    val text: String,
    val timestamp: Long
)

@Dao
interface AstroDao {
    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfileEntity)

    // Daily Tarot
    @Query("SELECT * FROM daily_tarot WHERE dateString = :date LIMIT 1")
    suspend fun getDailyTarot(date: String): DailyTarotEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDailyTarot(dailyTarot: DailyTarotEntity)

    // Saved Readings
    @Query("SELECT * FROM saved_readings ORDER BY timestamp DESC")
    fun getAllSavedReadings(): Flow<List<SavedReadingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReading(reading: SavedReadingEntity): Long

    // Chat Messages
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getChatMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    @Query("DELETE FROM chat_messages")
    suspend fun clearChatHistory()
}

@Database(
    entities = [
        UserProfileEntity::class,
        DailyTarotEntity::class,
        SavedReadingEntity::class,
        ChatMessageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AstroDatabase : RoomDatabase() {
    abstract fun astroDao(): AstroDao

    companion object {
        @Volatile
        private var INSTANCE: AstroDatabase? = null

        fun getInstance(context: Context): AstroDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AstroDatabase::class.java,
                    "astro_oracle_database.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
