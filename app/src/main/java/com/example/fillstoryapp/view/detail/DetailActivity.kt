package com.example.fillstoryapp.view.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fillstoryapp.data.response.story.ListStoryItem
import com.example.fillstoryapp.databinding.ActivityDetailBinding

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val detail = intent.getParcelableExtra<ListStoryItem>(DETAIL_STORY) as ListStoryItem
        setupAction(detail)

        supportActionBar?.show()
        supportActionBar?.title = "Detail Story"
    }

    private fun setupAction(detail: ListStoryItem){
        Glide.with(applicationContext)
            .load(detail.photoUrl)
            .into(bind.imgStory)
        bind.tvName.text = detail.name
        bind.tvDesc.text = detail.description
        bind.progressBar.visibility = View.GONE
    }

    companion object {
        const val DETAIL_STORY = "detail_story"
    }
}