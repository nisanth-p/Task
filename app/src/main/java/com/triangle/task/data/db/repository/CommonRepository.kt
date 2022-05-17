package com.triangle.task.data.db.repository

import androidx.paging.PagingData
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CommonRepository {

    suspend fun getAllImages(language:String): Flow<PagingData<DataItem>>
}