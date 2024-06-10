package com.example.fillstoryapp


import com.example.fillstoryapp.data.response.story.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "2023-09-03T14:00:00Z",
                "User $i",
                "description $i",
                1.0,
                "story-$i",
                "author + $i",
                "quote $i",
                1.0
            )
            items.add(quote)
        }
        return items
    }
}