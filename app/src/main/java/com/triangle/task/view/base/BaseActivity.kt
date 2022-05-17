package com.triangle.task.view.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.triangle.task.R
import kotlinx.coroutines.DelicateCoroutinesApi

private const val TAG = "@@BaseActivity"
@DelicateCoroutinesApi
abstract class BaseActivity<VB : ViewBinding,VM : BaseViewModel> : AppCompatActivity()
    {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sharedModel by viewModels<BaseViewModel>()
    val _sharedModel get() = sharedModel

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    abstract fun setup()
    abstract fun getViewModel(): VM
    @LayoutRes
    abstract fun layoutRes(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        createNavControl()
        setup()
    }

    protected fun findNavHostFragment() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
    private fun createNavControl() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    fun nav(id: Int) {
        navController.navigate(id)
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
        _binding = null

    }

}

