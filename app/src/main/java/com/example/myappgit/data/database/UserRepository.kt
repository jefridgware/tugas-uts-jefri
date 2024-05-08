package com.example.myappgit.data.database

import androidx.lifecycle.LiveData
import com.example.myappgit.data.FavoriteUser
import com.example.myappgit.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepository private constructor(private val userDao: UserDao) {

    suspend fun addFavoriteUser(user: FavoriteUser) {
        withContext(Dispatchers.IO) {
            userDao.addFavoriteUser(user)
        }
    }

    suspend fun removeFavoriteUser(userId: Int) {
        withContext(Dispatchers.IO) {
            userDao.removeUser(userId)
        }
    }

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return userDao.getFavoriteUsers()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }
}