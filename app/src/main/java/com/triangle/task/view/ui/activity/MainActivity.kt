package com.triangle.task.view.ui.activity

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.triangle.task.R
import com.triangle.task.databinding.ActivityMainBinding
import com.triangle.task.view.base.BaseActivity
import com.triangle.task.view.base.BaseViewModel
import com.triangle.task.view.base.NavigationHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(),
    NavigationHost {
    val viewModel by viewModels<MainActivityModel>()
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
    private var viewBinding: ActivityMainBinding? = null
    override fun setup() {
        viewBinding = binding
      //  onBackPressed()
    }

    override fun getViewModel(): BaseViewModel  =viewModel

    override fun layoutRes(): Int = R.layout.activity_main
    override fun findNavControl() = findNavHostFragment()?.findNavController()
    override fun hideNavigation(animate: Boolean) {
    }

    override fun showNavigation(animate: Boolean) {
    }

    private fun setupBackPressed() {
        val dispatcher = onBackPressedDispatcher
        dispatcher.addCallback(this) {
            isEnabled = false
                    findNavControl()?.run{
                        when (currentDestination?.id) {
                            R.id.selectedImageFragment -> {
                                nav(R.id.action_selectedImageFragment_to_homeFragment)
                            }
                            R.id.homeFragment -> {
                                showWhatsNewDialog(onBackPressedCallback = this@addCallback)
                            }
                            else -> {
                                remove()
                                onBackPressed()
                            }
                        }
                    }

            }

 }


    override fun onBackPressed() {
        super.onBackPressed()
        setupBackPressed()
    }

    private fun showWhatsNewDialog(onBackPressedCallback: OnBackPressedCallback) {
        MaterialAlertDialogBuilder(this, R.style.AlertDialog)
            .setBackground(ContextCompat.getDrawable(this, R.drawable.bg_dialog))
            .setView(R.layout.layout_empty_box_msg)
            .setCancelable(false)
            .setMessage("Are you want to exit the app?")
            .setPositiveButton(R.string.textYes) { _, _ ->
                onBackPressedCallback.remove()
                finish()
            }
            .setNegativeButton(R.string.textCancel) { _, _ -> }
            .show()
    }

}