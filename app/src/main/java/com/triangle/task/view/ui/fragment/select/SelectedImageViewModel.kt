package com.triangle.task.view.ui.fragment.select

import androidx.lifecycle.ViewModel
import com.triangle.task.data.db.api.ApiService
import com.triangle.task.data.db.repository.CommonRepository
import com.triangle.task.data.utill.NetworkHelper
import com.triangle.task.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class SelectedImageViewModel  @Inject constructor(private val apiService: ApiService, private val repository: CommonRepository, override val networkHelper: NetworkHelper) : BaseViewModel(networkHelper) {

}