package com.example.backgroundapp.network

import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashService {
    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("per_page") perPage: Int
    ): List<PhotoItem>

    @GET("search/photos")
    suspend fun categoryPhotos(
        @Query("query") category: String,
        @Query("client_id") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<SearchResponse>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") keyword: String,
        @Query("client_id") apiKey: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<SearchResponse>

    @GET("photos/{id}")
    suspend fun getImageDetail(
        @Path("id") id: String
    ): Response<PhotoItem>
}
