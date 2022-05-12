package com.triangle.task.view.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.triangle.task.R
import com.triangle.task.data.utill.AppUtils
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private const val TAG = "@@BaseActivity"
@DelicateCoroutinesApi
abstract class BaseActivity<VB : ViewBinding,VM : BaseViewModel> : AppCompatActivity(),
    BaseFragment.Callback {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sharedModel by viewModels<BaseViewModel>()
    val _sharedModel get() = sharedModel
    var mProgressDialog: Dialog? = null
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



    fun View.showSnackbar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: CharSequence?,
        action: (View) -> Unit
    ) {
        val snackbar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                action(this)
            }.show()
        } else {
            snackbar.show()
        }
    }

    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
        val sbView = snackbar.view
        val textView = sbView
            .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    private fun redSnackBar(message: String = getString(R.string.error_internet_connetion)) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setTextColor(Color.WHITE).setBackgroundTint(Color.RED)
            .show()
    }
    fun onError(message: String?) {
        if (message != null) {
            showSnackBar(message)
        } else {
            showSnackBar(getString(R.string.some_error))
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission: ", "Granted")
            } else {
                Log.i("Permission: ", "Denied")
            }
        }



    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
            mProgressDialog?.cancel()
        }
    }

    fun showLoading() {
        if (_sharedModel.networkHelper.isNetworkConnected()) {
            hideLoading()
            mProgressDialog = AppUtils.showLoadingDialog(this)
        } else {
          //  redSnackBar()
        }
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}

