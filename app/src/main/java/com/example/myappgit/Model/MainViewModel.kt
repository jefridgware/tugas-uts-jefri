package com.example.myappgit.Model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myappgit.response.GitHubResponse
import com.example.myappgit.response.ItemsItem
import com.example.myappgit.response.UserDetailResponse
import com.example.myappgit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {


    private val _listReview = MutableLiveData<List<ItemsItem>>()
    val listReview: LiveData<List<ItemsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val userdetail = MutableLiveData<UserDetailResponse>()
    val UserDetail: LiveData<UserDetailResponse> = userdetail

    private val followers = MutableLiveData<List<ItemsItem>>()
    val getFollowers: LiveData<List<ItemsItem>> = followers

    private val following = MutableLiveData<List<ItemsItem>>()
    val getFollowing: LiveData<List<ItemsItem>> = following


    companion object {
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "username"
    }

    init {
        findGitHub()
    }

    private fun findGitHub() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGitHub(RESTAURANT_ID)
        client.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listReview.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "\"Failed to perform search: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Network error: ${t.message}")
            }
        })
    }

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val userDetail = response.body()
                    userdetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun Followers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    followers.value = response.body()
                    followers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


    fun Following(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    following.value = response.body()
                    following.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}


