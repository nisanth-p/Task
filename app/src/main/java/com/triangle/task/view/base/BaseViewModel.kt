package com.triangle.task.view.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.utill.NetworkHelper
import com.triangle.task.data.utill.impl.IPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

private const val TAG = "BaseViewModel"
@DelicateCoroutinesApi
@HiltViewModel
open class BaseViewModel @Inject constructor(open val networkHelper: NetworkHelper) :
    ViewModel(), DefaultLifecycleObserver {
    @Inject
    open lateinit var sharedPref: IPref
    var imagelist: MutableLiveData<MutableList<DataItem>> =MutableLiveData<MutableList<DataItem>>()
    var showProgress: MutableLiveData<Boolean> =MutableLiveData<Boolean>()

}