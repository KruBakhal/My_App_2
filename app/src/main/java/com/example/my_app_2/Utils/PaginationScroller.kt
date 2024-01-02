package com.example.newappdi.NewsApp.Utils

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScroller(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    val PAGE_START = 1

    var isScrolling: Boolean = false


    /**
     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
     */
    var PAGE_SIZE = 5

    /**
     * Supporting only LinearLayoutManager for now.
     */


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val isNotLoadingAndNotLastPage = !isLoading() && !isLastPage()
        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val isTotalMoreThanVisible = totalItemCount >= PAGE_SIZE
        val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                isTotalMoreThanVisible && isScrolling
        if (shouldPaginate) {
            loadMoreItems()
            isScrolling = false
        } else {
            recyclerView.setPadding(0, 0, 0, 0)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    protected abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean


}