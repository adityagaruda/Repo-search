package com.kaisebhi.githubproject.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.network.ContributorModel
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.data.room.DatabaseRoom
import com.kaisebhi.githubproject.utils.NetworkUtils
import com.kaisebhi.githubproject.utils.NetworkUtils.Companion.TAG
import com.kaisebhi.githubproject.utils.ResponseHandler
import retrofit2.Response

class RecentDetailsRepository(
    private val ctx: Context,
    private val apiInterface: ApiInterface,
    private val db: DatabaseRoom
) {
    private val mutableDetLiveData: MutableLiveData<ResponseHandler<AllRepoEntity>> =
        MutableLiveData()
    val detLiveData: LiveData<ResponseHandler<AllRepoEntity>>
        get() {
            return mutableDetLiveData
        }
    private val mutableContLiveData: MutableLiveData<ResponseHandler<List<ContributorModel>>> =
        MutableLiveData()
    val contLiveData: LiveData<ResponseHandler<List<ContributorModel>>>
        get() {
            return mutableContLiveData
        }

    suspend fun searchDetData(url: String) {
        mutableDetLiveData.value = ResponseHandler.Loading()
        //get data from room
        try {
            mutableDetLiveData.value = ResponseHandler.Success(db.getAllRepoDao().whereQuery(url))
            Log.d(TAG, "searchDetData: success ${mutableDetLiveData.value}")
        } catch (e: Exception) {
            mutableDetLiveData.value = ResponseHandler.Error("Local db error")
            Log.d(TAG, "searchDetData: $e")
        }
    }

    suspend fun getContributors(bearerToken: String, contributorUrl: String) {
        mutableContLiveData.value = ResponseHandler.Loading()
        if (NetworkUtils.getNetworkState(ctx)) {
            try {
                val res: Response<List<ContributorModel>> =
                    apiInterface.getContributors(bearerToken, contributorUrl)
                if (res.isSuccessful) {
                    res.body()?.let {
                        mutableContLiveData.value = ResponseHandler.Success(it)
                    } ?: run {
                        mutableContLiveData.value = ResponseHandler.Error("Error")
                    }
                } else {
                    mutableContLiveData.value = ResponseHandler.Error("Error")
                }
            } catch (e: Exception) {
                mutableContLiveData.value = ResponseHandler.Error("Error");
                Log.d(TAG, "addRepo: $e")
            }
        } else {
            mutableContLiveData.value = ResponseHandler.Error("Error");
        }
    }

}