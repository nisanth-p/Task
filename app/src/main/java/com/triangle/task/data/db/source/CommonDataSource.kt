package com.triangle.task.data.db.source

import androidx.paging.PagingData
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CommonDataSource {
     suspend fun getAllImages(language:String): Flow<PagingData<DataItem>>
}