package com.kaisebhi.githubproject.data.network

import com.kaisebhi.githubproject.data.room.ResponseModel
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @Headers("Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28")
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Header("Authorization") authorization: String,
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<ResponseModel>

    @Headers("Accept: application/vnd.github+json", "X-GitHub-Api-Version: 2022-11-28")
    @GET
    suspend fun getContributors(@Header("Authorization") authorization: String, @Url url: String): Response<List<ContributorModel>>
}