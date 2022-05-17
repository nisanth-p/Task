package com.triangle.task.data.db.remote

import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.db.source.CommonDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RemoteDataStoreImpl
    @Inject constructor(private val remote: ApiService,
                        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) :
    CommonDataSource