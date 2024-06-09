package com.example.fillstoryapp.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fillstoryapp.data.repo.UserRepository
import com.example.fillstoryapp.data.response.Result
import com.example.fillstoryapp.data.response.auth.SignupResponse

class SignupViewModel (private val repository: UserRepository) : ViewModel() {
    fun signup(name: String, email: String, pass: String): LiveData<Result<SignupResponse>> =
        repository.signup(name, email, pass)
}

