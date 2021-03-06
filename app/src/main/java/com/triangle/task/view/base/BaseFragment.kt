package com.triangle.task.view.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.triangle.task.R
import com.triangle.task.data.utill.NetworkHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import javax.inject.Inject


private const val TAG = "BaseFragment"
@DelicateCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {
    val sharedModel: BaseViewModel by activityViewModels()
    private val _sharedModel get() = sharedModel
    private lateinit var navController: NavController
    protected lateinit var navHostFragment: NavHostFragment
    private var _binding: ViewBinding? = null
    var mActivity: BaseActivity<VB, VM>? = null
    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    abstract fun setup()
    abstract fun getViewModels(): VM
    abstract fun getLifeCycleOwner(): LifecycleOwner
    @LayoutRes
    abstract fun layoutRes(): Int
    @Inject
    lateinit var networkHelper: NetworkHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        createNavControl()
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }
    private fun createNavControl() {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }



    fun nav(id: Int, bundle: Bundle = Bundle.EMPTY) {
        findNavController().navigate(id, bundle)

    }

    private fun findNavControl() =
        (requireActivity() as NavigationHost).findNavControl()


    override fun onResume() {
        super.onResume()
        setupBackPressed()
    }

    protected open fun setupBackPressed() {
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner) {
            isEnabled = false
            findNavControl()?.run{
                when (currentDestination?.id) {
                    R.id.selectedImageFragment -> {
                        Log.d(TAG, "setupBackPressed: selectedImageFragment")
                        nav(R.id.action_selectedImageFragment_to_homeFragment)
                    }
                    R.id.homeFragment -> {
                        Log.d(TAG, "setupBackPressed: homeFragment")
                        showWhatsNewDialog(onBackPressedCallback = this@addCallback)
                    }
                    else -> {
                        Log.v(TAG, "setupBackPressed: currentDestination?.id =${currentDestination?.id}")
                        remove()
                        onBackPressed()
                    }
                }
            }
        }
    }
    fun onBackPressed() {
        if (!navController.popBackStack()) {
            activity?.finish()
        }}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

    private fun showWhatsNewDialog(onBackPressedCallback: OnBackPressedCallback) {
        MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialog)
            .setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_dialog))
            .setView(R.layout.layout_empty_box_msg)
            .setCancelable(false)
            .setMessage("Are you want to exit the app?")
            .setPositiveButton(R.string.textYes) { _, _ ->
                onBackPressedCallback.remove()
                activity?.finish()
            }
            .setNegativeButton(R.string.textCancel) { _, _ ->
                nav(R.id.action_homeFragment_self)

            }
            .show()
    }
    fun showSnackBar(message: String) {
        val snackbar = activity?.let {
            Snackbar.make(
                it.findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT
            )
        }
        val sbView = snackbar?.view
        val textView = sbView
            ?.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        snackbar.show()
    }
}


