package com.triangle.task.view.base

import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.triangle.task.data.utill.IPref
import com.triangle.task.data.utill.NetworkHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject


private const val TAG = "BaseLifecycleObserver"
@DelicateCoroutinesApi
class BaseLifecycleObserver(private val context: Activity, private val registry: ActivityResultRegistry,  override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper),DefaultLifecycleObserver {

    lateinit var getContent: ActivityResultLauncher<String>
    override fun onCreate(owner: LifecycleOwner) {

    }
    fun selectImage(func:(file:Any)->Any) {
        func(getContent.launch("image/*"))
    }

}
