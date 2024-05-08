package com.example.myappgit.retrofit

import com.example.myappgit.response.GitHubResponse
import com.example.myappgit.response.ItemsItem
import com.example.myappgit.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getGitHub(
        @Query("q") username: String
    ): Call<GitHubResponse>
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetailResponse>
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}