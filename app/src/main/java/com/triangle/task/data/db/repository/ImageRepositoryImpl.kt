package com.triangle.task.data.db.repository

import com.triangle.task.data.db.source.CommonDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: CommonDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO):
    CommonRepository {


}