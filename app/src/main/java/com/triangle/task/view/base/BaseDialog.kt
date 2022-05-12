package com.triangle.task.view.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.triangle.task.data.utill.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
abstract class BaseDialog<T : ViewBinding, V : BaseViewModel> : DialogFragment(),CoroutineScope {

    var mActivity: BaseActivity<T, V>? = null
     val networkHelper: NetworkHelper? = null
    override fun onAttach(context: Context) {
        if (context != null) {
            super.onAttach(context)
        }
        if (context is BaseActivity<*, *>) {
            mActivity = context as BaseActivity<T, V>?
            mActivity?.onFragmentAttached()
        }
    }


    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        // creating the fullscreen dialog
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }



    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String) {
        dismiss()
        getBaseActivity()?.onFragmentDetached(tag)
    }

    private fun getBaseActivity(): BaseActivity<T, V>? = mActivity

    fun showLoading() = mActivity?.showLoading()

    fun hideLoading() = mActivity?.hideLoading()

    fun hideKeyboard() = mActivity?.hideKeyboard()
}