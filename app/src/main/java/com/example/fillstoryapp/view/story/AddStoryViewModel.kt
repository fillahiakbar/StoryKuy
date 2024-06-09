package com.example.fillstoryapp.view.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.fillstoryapp.data.pref.UserModel
import com.example.fillstoryapp.data.repo.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel (private val repository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun postStory(multipart: MultipartBody.Part, desc: RequestBody, token: String) =
        repository.addStory(multipart, desc, token)
}