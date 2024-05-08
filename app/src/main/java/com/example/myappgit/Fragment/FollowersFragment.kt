package com.example.myappgit.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappgit.Adapter.FollowAdapter
import com.example.myappgit.Model.MainViewModel
import com.example.myappgit.databinding.FragmentFollowersBinding
import com.example.myappgit.ui.DetailUserActivity


class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String
    private var position: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowersBinding.bind(view)
        adapter = FollowAdapter()
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        position = args?.getInt(ARG_POSITION) ?: 0
        Log.d("cobaTag", "onViewCreated:$username")
        binding.rvFollowers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FollowersFragment.adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (position == 1) {
            viewModel.getFollowers.observe(viewLifecycleOwner) { followers ->
                followers?.let {
                    adapter.setList(it)
                    showLoading(false)
                }
            }
            viewModel.Followers(username)
        } else {
            viewModel.getFollowing.observe(viewLifecycleOwner) { following ->
                following?.let {
                    adapter.setList(it)
                    showLoading(false)
                }
            }
            viewModel.Following(username)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        val ARG_USERNAME: String = "username"
        const val ARG_POSITION: String = "position"
    }
}
