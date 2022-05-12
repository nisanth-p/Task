package com.triangle.task.view.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.triangle.task.R
import com.triangle.task.data.model.pages.UserPages
import com.triangle.task.databinding.FragmentHomeBinding
import com.triangle.task.view.base.BaseFragment
import com.triangle.task.view.ui.fragment.home.paging.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext
@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), CoroutineScope {
    private lateinit var imageAdapter: ImageAdapter
    private var adapter: ImageAdapter? = null
    companion object {
        fun newInstance() = HomeFragment()
    }
    val viewModel by viewModels<HomeViewModel>()
    override fun getViewModels(): HomeViewModel = viewModel
    override fun getLifeCycleOwner(): LifecycleOwner = this
    override fun layoutRes(): Int = R.layout.fragment_home
    private var viewbinding: FragmentHomeBinding? = null
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    val map:HashMap<String,String> = hashMapOf()
    override fun setup() {
        viewbinding = binding
        bindViews()
        collectUiState()
    }
    private fun bindViews() {
        imageAdapter=ImageAdapter(ImageAdapter.ImageDiffCallBack)
        viewbinding!!.recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            setHasFixedSize(true)
            adapter = imageAdapter

        }
    }

    private fun collectUiState() {
           // viewModel.getImages("en_US",1)
        lifecycleScope.launch(Dispatchers.IO) {
           /* viewModel.getAllImages("en_US",1).collectLatest { movies ->
                imageAdapter.submitData(movies )
            }*/
            viewModel.imagesList.collect {
                imageAdapter.submitData(it)
            }
        }
    }

}