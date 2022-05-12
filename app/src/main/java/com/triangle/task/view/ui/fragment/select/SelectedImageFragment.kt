package com.triangle.task.view.ui.fragment.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.triangle.task.R
import com.triangle.task.databinding.SelectedImageFragmentBinding
import com.triangle.task.view.base.BaseFragment
import com.triangle.task.view.ui.fragment.home.HomeViewModel
import com.triangle.task.view.ui.fragment.home.paging.ImageAdapter
import com.triangle.task.view.ui.fragment.select.adapter.SelectAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@DelicateCoroutinesApi
@AndroidEntryPoint
class SelectedImageFragment(

) : BaseFragment<SelectedImageFragmentBinding, SelectedImageViewModel>(), CoroutineScope {
    private lateinit var selectAdapter: SelectAdapter
    companion object {
        fun newInstance() = SelectedImageFragment()
    }

    val viewModel by viewModels<SelectedImageViewModel>()
    private var viewbinding: SelectedImageFragmentBinding? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private var globalLaunch: Job? = null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SelectedImageFragmentBinding
        get() = SelectedImageFragmentBinding::inflate

    override fun getViewModels(): SelectedImageViewModel = viewModel

    override fun getLifeCycleOwner(): LifecycleOwner = this

    override fun layoutRes(): Int = R.layout.selected_image_fragment


    override fun setup() {
        viewbinding = binding
        bindViews()
    }

    private fun bindViews() {
        selectAdapter = SelectAdapter(emptyList())
        sharedModel.imagelist.value?.let {  selectAdapter.submitList(it)}!!
        viewbinding!!.recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = selectAdapter

        }



    }
}