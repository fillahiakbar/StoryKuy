package com.example.fillstoryapp.view.maps

import androidx.lifecycle.ViewModel
import com.example.fillstoryapp.data.repo.UserRepository


class MapsViewModel(private val repository: UserRepository): ViewModel() {

    fun getLocations() = repository.getLocation()
}