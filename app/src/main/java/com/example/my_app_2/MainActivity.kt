package com.example.my_app_2

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.my_app_2.base.BaseActivity
import com.example.my_app_2.databinding.ActivityMainBinding
import com.example.newappdi.NewsApp.Adapter.NewAdapter
import com.example.my_app_2.model.ResponseData
import com.example.newappdi.NewsApp.Utils.PaginationScroller
import com.example.newappdi.NewsApp.ViewModel.NewViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    companion object {
        const val QUERY_PAGE_SIZE = 20
    }
    var isLoading = false
    var isLastPage = false
    private lateinit var newAdapter: NewAdapter
    val viewModel: NewViewModel by viewModels()

    override fun init(): Boolean {
        newAdapter = NewAdapter()
        newAdapter.setOnItemClickListner {
            val bundle = Bundle().apply {
                putString("article", Gson().toJson(it))
            }
        }
        binding.rvList.apply {
            adapter = newAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
        viewModel.breakingNew.observe(this@MainActivity) { response ->
            when (response) {
                is ResponseData.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults!! / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewPage == totalPages

                        if (isLastPage) {
                            binding.rvList.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is ResponseData.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->

                    }
                }

                is ResponseData.Loading -> {
                    showProgressBar()
                }

                else -> {}
            }
        }

        binding.rvList.addOnScrollListener(object : PaginationScroller(binding.rvList.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                callApiNew()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

        return false
    }

    private fun callApiNew() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getBreakingNews("us")
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar() {
        binding. progressBar.visibility = View.VISIBLE
        isLoading = true
    }

}