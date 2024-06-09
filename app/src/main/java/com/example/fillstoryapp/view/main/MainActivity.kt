package com.example.fillstoryapp.view.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fillstoryapp.R
import com.example.fillstoryapp.data.paging.LoadingStateAdapter
import com.example.fillstoryapp.databinding.ActivityMainBinding
import com.example.fillstoryapp.view.ViewModelFactory
import com.example.fillstoryapp.view.detail.DetailActivity
import com.example.fillstoryapp.view.maps.MapsActivity
import com.example.fillstoryapp.view.story.AddStoryActivity
import com.example.fillstoryapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addStory.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.actionMap.setOnClickListener{
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

        setupView()
        setupRecyclerView()
        setupViewModel()
        setupAction()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupRecyclerView() {
        storyAdapter = StoryAdapter()
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        binding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
        viewModel.quote.observe(this, { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        })
    }



    private fun setupViewModel() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                loadStories()
            }
        }

        viewModel.stories.observe(this) { stories ->
            Log.d("MainActivity", "Updating adapter with stories: $stories")
            storyAdapter.submitData(lifecycle, stories)
            binding.loadingProgressBar.visibility = View.GONE
        }
    }

    private fun setupAction() {
        binding.actionLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun loadStories() {
        binding.loadingProgressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.getStories()
        }
    }
}