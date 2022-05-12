package com.triangle.task.view.ui.activity

import android.view.LayoutInflater
import androidx.activity.viewModels
import com.triangle.task.R
import com.triangle.task.databinding.ActivityMainBinding
import com.triangle.task.view.base.BaseActivity
import com.triangle.task.view.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>() {
    val viewModel by viewModels<MainActivityModel>()
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    private var viewBinding: ActivityMainBinding? = null
    override fun setup() {
        viewBinding = binding
    }

    override fun getViewModel(): BaseViewModel  =viewModel

    override fun layoutRes(): Int = R.layout.activity_main



}