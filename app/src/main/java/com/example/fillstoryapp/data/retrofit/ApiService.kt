package com.example.fillstoryapp.data.retrofit

import com.example.fillstoryapp.data.response.story.AddStoryResponse
import com.example.fillstoryapp.data.response.story.GetStoryResponse
import com.example.fillstoryapp.data.response.auth.LoginResponse
import com.example.fillstoryapp.data.response.auth.SignupResponse
import com.example.fillstoryapp.data.response.story.ListStoryItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun signup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignupResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @POST("stories")
    suspend fun addStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse

    @GET("stories")
    suspend fun getStories(): GetStoryResponse

    @GET("stories")
    suspend fun getStoriesWithLocation(
        @Query("location") location: Int = 1,
    ): GetStoryResponse

    @GET("stories")
    suspend fun getQuote(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ListStoryItem>
}