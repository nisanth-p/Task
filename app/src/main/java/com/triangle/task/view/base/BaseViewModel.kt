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
    var image_uri: MutableLiveData<String> =MutableLiveData<String>()
    var imagelist: MutableLiveData<MutableList<DataItem>> =MutableLiveData<MutableList<DataItem>>()
    @Inject
    lateinit var gson: Gson
    @Inject
    open lateinit var sharedPref: IPref
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.i(TAG, "onStart: LifecycleOwner")

    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        Log.i(TAG, "onCreate: LifecycleOwner")

    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.i(TAG, "onResume: LifecycleOwner")

    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.i(TAG, "onStop: LifecycleOwner")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i(TAG, "onDestroy: LifecycleOwner")
    }

}