package com.loc.moviesfinder.di

import com.loc.moviesfinder.core_feature.data.remote.MoviesApi
import com.loc.moviesfinder.core_feature.data.repository.MoviesRepositoryImpl
import com.loc.moviesfinder.core_feature.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(
        moviesApi: MoviesApi,
    ): MoviesRepository = MoviesRepositoryImpl(moviesApi)

    @Provides
    fun provideMoviesApi(
        httpClient: OkHttpClient,
    ): MoviesApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
        .create(MoviesApi::class.java)

    @Provides
    fun provideHttpClient() = OkHttpClient
        .Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .build()

}