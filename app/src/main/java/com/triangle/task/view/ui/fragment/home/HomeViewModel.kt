package com.triangle.task.view.ui.fragment.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.db.repository.CommonRepository
import com.triangle.task.data.utill.NetworkHelper
import com.triangle.task.view.base.BaseViewModel
import com.triangle.task.view.ui.fragment.home.paging.ImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

private const val TAG = "xxxHomeViewModel"
@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService,private val repository: CommonRepository,override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

    val imagesList =   Pager(PagingConfig(pageSize =6)) {
        ImagePagingSource(apiService,this)
    }.flow.cachedIn(viewModelScope)
}