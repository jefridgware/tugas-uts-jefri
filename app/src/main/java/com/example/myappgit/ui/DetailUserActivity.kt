package com.example.myappgit.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myappgit.Adapter.SectionPagerAdapter
import com.example.myappgit.Model.MainViewModel
import com.example.myappgit.R
import com.example.myappgit.data.FavoriteUser
import com.example.myappgit.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username == null) {
            finish()
            return
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.findDetailUser(username)
        showLoading(true)
        viewModel.UserDetail.observe(this, { userDetail ->
            userDetail?.let {
                showLoading(false)
                binding.apply {
                    progressBar.visibility = View.VISIBLE
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowers.text = "   ${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .centerCrop()
                        .into(imageView)
                }
            }
        })
        supportActionBar?.elevation = 0f
        setupViewPager(username)
    }



    private fun setupViewPager(username: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = username

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE
            else View.GONE
    }
    companion object {
        const val EXTRA_USERNAME = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}