package com.triangle.task.view.base

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.utill.IPref
import com.triangle.task.data.utill.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

private const val TAG = "BaseViewModel"
@DelicateCoroutinesApi
@HiltViewModel
open class BaseViewModel @Inject constructor(open val networkHelper: NetworkHelper) :
    ViewModel(), DefaultLifecycleObserver {

    var imagelist: MutableLiveData<MutableList<DataItem>> =MutableLiveData<MutableList<DataItem>>()
    @Inject
    lateinit var gson: Gson
    @Inject
    open lateinit var sharedPref: IPref


}