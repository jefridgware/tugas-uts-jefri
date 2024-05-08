package com.example.myappgit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappgit.Model.MainViewModel
import com.example.myappgit.R
import com.example.myappgit.databinding.ActivityMainBinding
import com.example.myappgit.response.GitHubResponse
import com.example.myappgit.response.ItemsItem
import com.example.myappgit.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ReviewAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        adapter = ReviewAdapter()
        adapter.setOnItemClickCallback(object : ReviewAdapter.OnItemClickCallback {
            override fun onItemClicked(userActivity: ItemsItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, userActivity.login)
                    startActivity(it)
                }
            }
        })
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu -> {
                    val intent = Intent(this@MainActivity, ModeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        recyclerView = findViewById(R.id.rvReview)
        recyclerView.adapter = adapter

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { textView, actionId, event ->
                searchBar.setText(searchView.text)
                searchView.hide()
                val Query = searchView.text.toString()
                Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                Search(Query)
                true
            }
        }

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }


    private fun setReviewData(consumerReviews: List<ItemsItem>) {
        adapter.submitList(consumerReviews)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun Search(query: String) {
        showLoading(true)
        val service = ApiConfig.getApiService()
        val call = service.getGitHub(query)
        call.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val gitHubResponse = response.body()
                    gitHubResponse?.let {
                        val items = it.items
                        setReviewData(items)
                    }
                } else {
                    Log.e(TAG, "Failed to perform search: ${response.message()}")
                    Toast.makeText(
                        this@MainActivity,
                        "Failed to perform search",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "Network error: ${t.message}")
                Toast.makeText(
                    this@MainActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
