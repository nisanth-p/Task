package com.triangle.task.view.ui.fragment.home.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


private const val TAG = "xxxImagePagingSource"

class ImagePagingSource(
    private val service: ApiService,
) : PagingSource<Int, DataItem>() {
    @Inject
    lateinit var gson: Gson
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        try {
            var response: Response<UserPages>? = null
            val nextPageNumber = params.key ?: 1

             try {
                 Log.d(TAG, "**********NextPage: " + nextPageNumber)
                response = service.getImages(nextPageNumber)
            }catch (e:Exception) {

            }
            Log.d(TAG, "**********load: " + response)
            val res = mutableListOf<DataItem>()
            if (response != null) {
                if (response.isSuccessful) {
                    Log.d(TAG, "**********load: " + response)
                }else{
                    Log.d(TAG, "**********load: " + response)
                }
            }

            val data = response?.body()?.myData ?: emptyList()
            res.addAll(data)
            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1
            Log.d(TAG, "**********load #######: " + res)
            return LoadResult.Page(
                data = res,
                prevKey = null,
                nextKey = nextPageNumber.plus(1)
            )
        } catch (exception: IOException) {
            Log.d(TAG, "load: Error =" + exception.message)
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.d(TAG, "load: Error =" + exception.message())
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        Log.d(TAG, "getRefreshKey: ")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}