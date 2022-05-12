package com.triangle.task.data.db.repository

import androidx.paging.PagingData
import com.triangle.task.data.db.source.CommonDataSource
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: CommonDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):
    CommonRepository {
    override suspend fun getImages(language:String ,page:Int): Response<UserPages>? {
     return  dataSource.getImages(language ,page)
    }

    override suspend fun getAllImages(language: String): Flow<PagingData<DataItem>> {
        return dataSource.getAllImages(language)
    }

}