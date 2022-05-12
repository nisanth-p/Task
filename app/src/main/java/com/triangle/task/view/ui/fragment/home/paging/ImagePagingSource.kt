package com.triangle.task.view.ui.fragment.home.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

private const val TMDB_STARTING_PAGE_INDEX = 1
private const val TAG = "ImagePagingSource"
class ImagePagingSource(
    private val service: ApiService,
    private val query: String
) : PagingSource<Int, DataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = service.getImages( nextPageNumber)
           val res = mutableListOf<DataItem>()
            if (response.isSuccessful) {
                Log.d(TAG, "load: "+response)
            }

           val data = response.body()?.myData ?: emptyList()
           res.addAll(data)
           val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1

           return LoadResult.Page(
               data = res,
               prevKey = prevKey,
               nextKey = nextPageNumber.plus(1)
           )
        } catch (exception: IOException) {
           return LoadResult.Error(exception)
       } catch (exception: HttpException) {
           return LoadResult.Error(exception)
       }
    }
    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}