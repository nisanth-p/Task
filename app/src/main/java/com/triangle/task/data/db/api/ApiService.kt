package com.triangle.task.data.db.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.triangle.task.data.model.pages.UserPages
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface ApiService{
    @GET("api/users")
    suspend fun getImages( @Query("page") pageNumber: Int) : Response<UserPages>
    companion object {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}