package com.triangle.task.view.ui.fragment.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import com.triangle.task.view.base.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


private const val TAG = "xxxImagePagingSource"
@DelicateCoroutinesApi
class ImagePagingSource(
    private val service: ApiService,
    private var viewModel: BaseViewModel,
) : PagingSource<Int, DataItem>() {
    @Inject
    lateinit var gson: Gson
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        try {
            var response: Response<UserPages>? = null
            val nextPageNumber = params.key ?: 1
             try {
                response = service.getImages(nextPageNumber)
            }catch (e:Exception) {
                 viewModel.showProgress.value = true
            }
            val res = mutableListOf<DataItem>()
            val data = response?.body()?.myData ?: emptyList()
            res.addAll(data)
            val prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1

            return if (res.size!=0) {
                viewModel.showProgress.value = false
                LoadResult.Page(
                    data = res,
                    prevKey = prevKey,
                    nextKey = nextPageNumber.plus(1)
                )
            }else{
                viewModel.showProgress.value = true
                LoadResult.Invalid()

            }
        } catch (exception: IOException) {
            viewModel.showProgress.value = false
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            viewModel.showProgress.value = false
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {

        viewModel.showProgress.value = false
      return  state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}