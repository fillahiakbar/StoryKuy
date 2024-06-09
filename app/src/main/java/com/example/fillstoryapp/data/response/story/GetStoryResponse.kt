package com.example.fillstoryapp.data.response.story

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetStoryResponse(

	@field:SerializedName("listStory")
	val story: List<ListStoryItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Entity(tableName = "story")
@Parcelize
data class ListStoryItem(
	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: String,

	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("en")
	val en: String? = null,

	@field:SerializedName("lat")
	val lat: String
): Parcelable
