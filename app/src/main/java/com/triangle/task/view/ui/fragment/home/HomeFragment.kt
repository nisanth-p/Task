package com.triangle.task.view.ui.fragment.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager

import com.triangle.task.R
import com.triangle.task.databinding.FragmentHomeBinding
import com.triangle.task.view.base.BaseFragment
import com.triangle.task.view.ui.fragment.home.paging.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val TAG = "xxxHomeFragment"

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), CoroutineScope {
    private lateinit var imageAdapter: ImageAdapter


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
    private var globalLaunch: Job? = null
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    val map: HashMap<String, String> = hashMapOf()
    override fun setup() {
        viewbinding = binding
        viewbinding!!.CLProgressbar2.visibility = View.VISIBLE
        lifecycleScope.launch { bindViews() }
        lifecycleScope.launch { collectUiState() }

    }

    private fun bindViews() {
        imageAdapter = ImageAdapter(context, sharedModel, ImageAdapter.ImageDiffCallBack)
        viewbinding!!.recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = imageAdapter
        }

        viewbinding!!.fabSelect.setOnClickListener {
            if (sharedModel.imagelist.value?.isNotEmpty() == true && sharedModel.imagelist.value?.size != 0)
                nav(R.id.action_homeFragment_to_selectedImageFragment)
            else  showSnackBar("Please Select it... ")
        }

    }

    private fun collectUiState() {
        globalLaunch = GlobalScope.launch(coroutineContext) {

           launch { viewModel._str.value = getFileFromAsset("resDemo.json", context) }
            launch {
                viewModel.imagesList.collect {
                    try {
                        imageAdapter.submitData(it)
                    } catch (e: Exception) {
                        Log.d(TAG, "collectUiState: " + e.message)
                    }
                }
            }
        }
        viewbinding!!.CLProgressbar2.visibility = View.GONE
    }

    override fun onDestroy() {
        viewbinding = null
        job.cancel()
        globalLaunch?.cancel()
        super.onDestroy()

    }

}