package com.example.myappgit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myappgit.data.FavoriteUser
import com.example.myappgit.data.UserDao

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoritDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): UserDao

    companion object {
        @Volatile
        private var instance: FavoritDatabase? = null

        fun getDatabase(context: Context): FavoritDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritDatabase::class.java,
                    "favorite_database"
                ).build()
                this.instance = instance
                instance
            }
        }
    }
}