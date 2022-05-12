package com.triangle.task.view.ui.activity

import com.triangle.task.data.utill.NetworkHelper
import com.triangle.task.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject
@DelicateCoroutinesApi
@HiltViewModel
class MainActivityModel @Inject constructor(override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {
}