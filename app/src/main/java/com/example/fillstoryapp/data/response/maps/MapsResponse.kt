package com.example.fillstoryapp.data.response.maps

import com.google.gson.annotations.SerializedName

data class MapsResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
