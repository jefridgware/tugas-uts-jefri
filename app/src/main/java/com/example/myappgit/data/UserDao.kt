package com.example.myappgit.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(user: FavoriteUser)

    @Query("DELETE FROM favorite_user WHERE id = :userId")
    suspend fun removeUser(userId: Int)

    @Query("SELECT * FROM favorite_user ORDER BY id ASC")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>
}