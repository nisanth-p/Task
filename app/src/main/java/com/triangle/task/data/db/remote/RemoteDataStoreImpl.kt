package com.triangle.task.data.db.remote

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.triangle.task.data.db.source.CommonDataSource
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import com.triangle.task.view.ui.fragment.home.paging.ImagePagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
const val NETWORK_PAGE_SIZE = 25
class RemoteDataStoreImpl
    @Inject constructor(private val remote: ApiService,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    CommonDataSource {

    override suspend fun getImages( language:String ,page:Int): Response<UserPages> {
        return remote.getImages(page)
    }

    override suspend fun getAllImages(language:String): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(service = remote,language)
            }
        ).flow


    }


}