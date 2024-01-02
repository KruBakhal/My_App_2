package com.example.newappdi.NewsApp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.my_app_2.di.repository.Repository
import com.example.newappdi.NewsApp.Model.NewsResponse
import com.example.my_app_2.model.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    private var breakingNewsResponse: NewsResponse? = null
    private var searchNewsResponse: NewsResponse? = null
    val breakingNew: MutableLiveData<ResponseData<NewsResponse>?> = MutableLiveData()
    var breakingNewPage: Int = 1

    val searchNews: MutableLiveData<ResponseData<NewsResponse>> = MutableLiveData()
    var searchNewPage: Int = 1
    var favStatus = MutableLiveData<Boolean>()

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNew.postValue(ResponseData.Loading())
            val response = repository.getBreakingNew(countryCode, breakingNewPage)
            val resposValue = handleBreakingResponse(response)
            Log.d("TAG", "getBreakingNews: " + resposValue)
            breakingNew.postValue(resposValue)
        }

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        viewModelScope.launch {
            searchNews.postValue(ResponseData.Loading())
            val response = repository.getSearchNew(searchQuery, searchNewPage)
            searchNews.postValue(handleSearchNewsResponse(response))
        }
    }

    private fun handleBreakingResponse(response: Response<NewsResponse>): ResponseData<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                    breakingNewsResponse!!.totalResults=breakingNewsResponse!!.totalResults!!+ resultResponse.totalResults!!
                    breakingNewsResponse!!.articles = oldArticles!!
                }
                return ResponseData.Success(breakingNewsResponse!!)
            }
        }
        breakingNewPage--
        if (breakingNewPage <= 0) breakingNewPage = 1
        return ResponseData.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): ResponseData<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return ResponseData.Success(searchNewsResponse ?: resultResponse)
            }
        }
        searchNewPage--
        if (searchNewPage <= 0) searchNewPage = 1
        return ResponseData.Error(response.message())
    }


}