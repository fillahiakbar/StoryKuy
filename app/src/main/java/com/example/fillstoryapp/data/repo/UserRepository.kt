package com.example.fillstoryapp.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.fillstoryapp.data.paging.QuotePagingSource
import com.example.fillstoryapp.data.pref.UserModel
import com.example.fillstoryapp.data.pref.UserPreference
import com.example.fillstoryapp.data.response.story.AddStoryResponse
import com.example.fillstoryapp.data.response.auth.ErrorResponse
import com.example.fillstoryapp.data.response.story.GetStoryResponse
import com.example.fillstoryapp.data.response.story.ListStoryItem
import com.example.fillstoryapp.data.response.auth.LoginResponse
import com.example.fillstoryapp.data.response.auth.SignupResponse
import com.example.fillstoryapp.data.retrofit.ApiService
import com.example.fillstoryapp.data.response.Result
import com.example.fillstoryapp.data.retrofit.ApiConfig

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService

) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun getStories(): List<ListStoryItem>? {
        val response = apiService.getStories()
        return if (!response.error!!) response.story else null
    }


    fun getQuote(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                QuotePagingSource(apiService)
            }
        ).liveData
    }

    fun addStory(multipart: MultipartBody.Part, desc: RequestBody, token: String): LiveData<Result<AddStoryResponse>> =
        liveData { emit(Result.Loading)
            try {
                val response = ApiConfig.ApiService(token).addStory(multipart,desc)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                Log.d("addStories", e.message.toString())
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.message
                emit(Result.Error(errorMessage.toString()))
            }
        }

    fun signup(
        name: String, email: String, pass: String
    ): LiveData<Result<SignupResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.signup(name, email, pass)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("register", e.message.toString())
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, pass)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            Log.d("login", e.message.toString())
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(Result.Error(errorMessage.toString()))
        }
    }


    fun getLocation(): LiveData<Result<GetStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation(1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("ListStoryViewModel", "getStoriesWithLocation: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }




    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        fun getInstance(userPreference: UserPreference, apiService: ApiService) = UserRepository(userPreference, apiService)
    }
}