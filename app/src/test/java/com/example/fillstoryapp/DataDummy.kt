package com.example.fillstoryapp

import com.example.fillstoryapp.data.response.story.ListStoryItem

object DataDummy {
    fun generateStories() =
        List(100) {
            ListStoryItem(
                id = "$it",
                name = "name $it",
                description = "aku anak desa",
                photoUrl = "photo $it",
                createdAt = "createdAt $it",
                lat = "",
                lon = "",
            )
        }
}