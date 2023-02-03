package com.loc.moviesfinder.core_feature.data.remote

import com.loc.moviesfinder.BuildConfig.API_KEY
import com.loc.moviesfinder.core_feature.data.remote.dao.MovieDetailsResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.MoviesCollectionResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.SearchResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.cast.CastResponse
import com.loc.moviesfinder.core_feature.data.remote.dao.reviews.ReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/latest")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<MoviesCollectionResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<SearchResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
    ): Response<ReviewsResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCast(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = "en-US",
    ): Response<CastResponse>
}
