package com.triangle.task.view.base

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.triangle.task.data.utill.IPref
import com.triangle.task.data.utill.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

private const val TAG = "BaseViewModel"
@DelicateCoroutinesApi
@HiltViewModel
open class BaseViewModel @Inject constructor(open val networkHelper: NetworkHelper) :
    ViewModel(), DefaultLifecycleObserver {
    var image_uri: MutableLiveData<String> =MutableLiveData<String>()
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