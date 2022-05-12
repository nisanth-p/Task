package com.triangle.task.view.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.db.repository.CommonRepository
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import com.triangle.task.data.utill.NetworkHelper
import com.triangle.task.data.utill.responcehelper.Resource
import com.triangle.task.view.base.BaseViewModel
import com.triangle.task.view.ui.fragment.home.paging.ImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

private const val TAG = "xxxHomeViewModel"
@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService,private val repository: CommonRepository,override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

    val imagesList =   Pager(PagingConfig(pageSize = 6)) {
        ImagePagingSource(apiService,str.value!!)
    }.flow.cachedIn(viewModelScope)
    var _str = MutableLiveData<String>("1")

    val str: LiveData<String>
        get() = _str
}