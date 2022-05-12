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

private const val TAG = "HomeViewModel"
@DelicateCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(private val apiService: ApiService,private val repository: CommonRepository,override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

    fun getImages(language:String ,page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getImages(language,page).let {
                if (it != null) {
                    if (it.isSuccessful) {
                        Log.d(TAG, "getUserImage: IfSuccess => $it")
                        _res.postValue(Resource.success(it.body()))

                    }else{
                        Log.i(TAG, "getUserImage: elseFail = ${it.errorBody().toString()}")
                        _res.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            }
        }
    }

    suspend fun getAllImages(language:String ,page:Int):Flow<PagingData<DataItem>>{

          return    repository.getAllImages(language)
            .map { pagingData ->
                pagingData.map {
                    Log.d(TAG, "getAllImages: "+it)
                    //_res.postValue(Resource.success(it))
                     it

                }
            }
            .cachedIn(viewModelScope)
    }
/*fun gets():Flow<PagingData<DataItem>>{

        Pager(PagingConfig(pageSize = 6)) {
          return@Pager  ImagePagingSource(apiService,"1")
        }.flow.cachedIn(viewModelScope)


}*/
    val imagesList =   Pager(PagingConfig(pageSize = 6)) {
        ImagePagingSource(apiService,"1")
    }.flow.cachedIn(viewModelScope)

    private val _res = MutableLiveData<Resource<UserPages>>()

    val res: LiveData<Resource<UserPages>>
        get() = _res

    private val _resList = MutableLiveData<Resource<UserPages>>()

    val resList: LiveData<Resource<UserPages>>
        get() = _resList
}