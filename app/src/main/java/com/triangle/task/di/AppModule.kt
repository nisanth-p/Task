package com.triangle.task.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.triangle.task.data.db.api.ApiService

import com.triangle.task.data.db.remote.RemoteDataStoreImpl
import com.triangle.task.data.db.repository.CommonRepository
import com.triangle.task.data.db.repository.ImageRepositoryImpl
import com.triangle.task.data.db.source.CommonDataSource
import com.triangle.task.data.utill.IPref
import com.triangle.task.data.utill.PrefImpl
import com.triangle.task.data.utill.UrlConstants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteDataSource
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LocalDataSource
    @Singleton
    @Provides
    fun provideImageRepositoryImpl(

        @RemoteDataSource remote: CommonDataSource,
        ioDispatcher: CoroutineDispatcher
    ): CommonRepository {
        return ImageRepositoryImpl( remote, ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton // Provide always the same instance
    @Provides // Run this code when providing an instance of CoroutineScope
    fun providesCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)



    @RemoteDataSource
    @Singleton
    @Provides
    fun provideRemoteDataStoreImpl(remote: ApiService,
                                   ioDispatcher: CoroutineDispatcher): CommonDataSource = RemoteDataStoreImpl(remote, ioDispatcher)

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()
        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

/*    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }*/
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()

        .baseUrl(UrlConstants.BASE_APPSCRIPT_URL)
        .addConverterFactory(MoshiConverterFactory.create(ApiService.moshi))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


}
@Module
@InstallIn(SingletonComponent::class)

abstract class ResourceWrapperModule {
    @Singleton
    @Binds
    abstract fun bindPrefImpl(prefImpl: PrefImpl): IPref
}