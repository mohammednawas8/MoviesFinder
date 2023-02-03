package com.loc.moviesfinder.core_feature.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {

    @GET("t/p/w500/{poster_path}")
    suspend fun downloadImage(
        @Path("poster_path") imagePath: String
    ): Response<ResponseBody>
}