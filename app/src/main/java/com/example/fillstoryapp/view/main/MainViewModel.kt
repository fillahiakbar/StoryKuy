package com.example.fillstoryapp.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.fillstoryapp.data.pref.UserModel
import com.example.fillstoryapp.data.repo.UserRepository
import com.example.fillstoryapp.data.response.story.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    val quote: LiveData<PagingData<ListStoryItem>> =
        repository.getQuote().cachedIn(viewModelScope)

    private val _stories = MutableLiveData<List<ListStoryItem>>()
    val stories: LiveData<List<ListStoryItem>> = _stories

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }



    fun getStories() {
        viewModelScope.launch {
            try {
                val stories = repository.getStories()
                Log.d("MainViewModel", "Fetched stories: $stories")
                _stories.value = stories ?: emptyList()
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching stories: ${e.message}", e)
                _stories.value = emptyList()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}