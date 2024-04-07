package com.kaisebhi.githubproject.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kaisebhi.githubproject.data.network.ContributorModel
import com.kaisebhi.githubproject.data.repository.MainRepository
import com.kaisebhi.githubproject.data.repository.RecentDetailsRepository
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.utils.ResponseHandler
import kotlinx.coroutines.launch

class RentDetailsViewModel(private val repo: RecentDetailsRepository): ViewModel() {
    val detLiveData: LiveData<ResponseHandler<AllRepoEntity>>
        get() {
            return repo.detLiveData
        }
    val contLiveData: LiveData<ResponseHandler<List<ContributorModel>>>
        get() {
            return repo.contLiveData
        }

    fun getDetails(url: String) {
        viewModelScope.launch {
            repo.searchDetData(url)
        }
    }

    fun getContributors(bearerToken: String, url: String) {
        viewModelScope.launch {
            repo.getContributors(bearerToken, url)
        }
    }
}

class RentDetailsViewModelFactory(private val repo: RecentDetailsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RentDetailsViewModel(repo) as T
    }
}