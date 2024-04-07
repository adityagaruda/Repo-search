package com.kaisebhi.githubproject.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kaisebhi.githubproject.data.network.ApiInterface
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.data.room.DatabaseRoom

class RepoPagingSource(
    private val apiService: ApiInterface,
    private val query: String,
    private val bearerToken: String,
    private val db: DatabaseRoom
) : PagingSource<Int, AllRepoEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllRepoEntity> {
        val pageNumber = params.key ?: 1
        return try {
            val response = apiService.searchRepositories(bearerToken, query, params.loadSize, pageNumber)
            val repos = response.body()?.items ?: emptyList()
            if (repos.isNotEmpty()) {
                db.getAllRepoDao().deleteList()
                db.getAllRepoDao().insertList(repos)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (repos.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AllRepoEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}