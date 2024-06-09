package com.example.fillstoryapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fillstoryapp.data.pref.UserModel
import com.example.fillstoryapp.data.repo.UserRepository
import com.example.fillstoryapp.data.response.auth.LoginResponse
import com.example.fillstoryapp.data.response.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> {
        return repository.login(email, pass)
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}