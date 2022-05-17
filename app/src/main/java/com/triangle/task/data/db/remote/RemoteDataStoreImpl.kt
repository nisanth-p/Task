package com.triangle.task.data.db.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.db.source.CommonDataSource
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.view.ui.fragment.home.paging.ImagePagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 25
class RemoteDataStoreImpl
    @Inject constructor(private val remote: ApiService,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    CommonDataSource {

    override suspend fun getAllImages(language:String): Flow<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(service = remote)
            }
        ).flow
    }


}