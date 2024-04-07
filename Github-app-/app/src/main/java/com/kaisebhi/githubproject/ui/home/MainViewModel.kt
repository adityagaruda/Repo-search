package com.kaisebhi.githubproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kaisebhi.githubproject.data.paging.RepoPagingSource
import com.kaisebhi.githubproject.data.repository.MainRepository
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.utils.NetworkUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository): ViewModel() {

    val TAG = "MainViewModel.kt"


    private val _searchResults = MutableLiveData<PagingData<AllRepoEntity>>()
    val searchResults: LiveData<PagingData<AllRepoEntity>> = _searchResults
     fun searchRepo(bearerToken: String, query: String) {
        if (NetworkUtils.getNetworkState(repo.ctx)) {
            val newPager = Pager(
                PagingConfig(pageSize = 10)
            ) {
                RepoPagingSource(repo.apiInterface, query, bearerToken,repo.db)
            }.flow.cachedIn(viewModelScope)

           viewModelScope.launch {
               newPager.collectLatest {
                   _searchResults.value = it
               }
           }
        } else {
            viewModelScope.launch {
                // Assuming `query()` fetches the latest 15 items from the database.
                val cachedItems = repo.db.getAllRepoDao().query()
                // Convert List<AllRepoEntity> to PagingData<AllRepoEntity> and post value
                val pagingData = PagingData.from(cachedItems)
                _searchResults.value = pagingData
            }
        }
    }

}



class MainViewModelFactory(private val repo: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repo) as T
    }
}