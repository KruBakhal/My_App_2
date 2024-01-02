package com.example.my_app_2.di.repository

import com.example.newappdi.NewsApp.Model.NewsResponse
import com.example.newappdi.NewsApp.Repository.API_Interface
import com.example.newappdi.NewsApp.Utils.Constant
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: API_Interface) {


    suspend fun getBreakingNew(countryCode: String, pageNumber: Int): Response<NewsResponse> {
        return apiInterface.getBreakingNews(countryCode, pageNumber, Constant.API_KEY)
    }

    suspend fun getSearchNew(searchQuery: String, searchNewPage: Int): Response<NewsResponse> {
        return apiInterface.searchForNews(searchQuery, searchNewPage, Constant.API_KEY)
    }
}