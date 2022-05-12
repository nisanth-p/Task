package com.triangle.task.view.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.triangle.task.R
import com.triangle.task.data.utill.NetworkHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject


private const val TAG = "BaseFragment"
@DelicateCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    val sharedModel: BaseViewModel by activityViewModels()
    lateinit var observer: BaseLifecycleObserver
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = BaseLifecycleObserver(requireActivity(),requireActivity().activityResultRegistry, networkHelper)
        lifecycle.addObserver(observer)
    }

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

    fun takeImage(func:(file:Any)->Any) {

        observer.selectImage(){
            Log.d(TAG, "takeImage: $it")
            func(it)}
    }


    fun nav(id: Int, bundle: Bundle = Bundle.EMPTY) {
        findNavController().navigate(id, bundle)

    }

    private fun findNavControl() =
        (requireActivity() as NavigationHost).findNavControl()

    protected fun hideNavigation(animate: Boolean = true) =
        (requireActivity() as NavigationHost).hideNavigation(animate)

    protected fun showNavigation(animate: Boolean = true) =
        (requireActivity() as NavigationHost).showNavigation(animate)

    override fun onResume() {
        super.onResume()
        setupBackPressed()
    }

    protected open fun setupBackPressed() {
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner) {
            isEnabled = false
            findNavControl()?.run{

            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }
}


