package com.kaisebhi.githubproject.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.network.ContributorModel
import com.kaisebhi.githubproject.data.room.DatabaseRoom
import com.kaisebhi.githubproject.utils.NetworkUtils
import com.kaisebhi.githubproject.utils.ResponseHandler
import retrofit2.Response

class MainRepository(
     val ctx: Context,
     val apiInterface: ApiInterface,
     val db: DatabaseRoom
) {


    private val mutableContLiveData: MutableLiveData<ResponseHandler<List<ContributorModel>>> =
        MutableLiveData()


    private val TAG = "MainRepo.kt"



    suspend fun getContributors(bearerToken: String, contributorUrl: String) {
        mutableContLiveData.value = ResponseHandler.Loading()
        if (NetworkUtils.getNetworkState(ctx)) {
            try {
                val res: Response<List<ContributorModel>> = apiInterface.getContributors(bearerToken, contributorUrl)
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