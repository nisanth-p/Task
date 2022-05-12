package com.triangle.task.view.base.genericadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.triangle.task.R
import com.triangle.task.data.model.RecordsItem
import com.triangle.task.databinding.FragmentHomeBinding

private const val TAG = "yyyJavaViewHolderFactory"

object JavaViewHolderFactory {

    fun create(_viewBinding: ViewBinding?, view: View, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.fragment_home -> SelectedRoomDeviceViewHolder(_viewBinding as FragmentHomeBinding?,view)
            else -> SelectedRoomDeviceViewHolder(_viewBinding as FragmentHomeBinding?,view)
        }
    }


    // TODO: 30-12-2021 Room Selection

    class SelectedRoomDeviceViewHolder(viewBinding: FragmentHomeBinding?, itemView: View) :
        RecyclerView.ViewHolder(itemView),
        GenericAdapter.Binder<RecordsItem> {
        var binding = FragmentHomeBinding.bind(itemView)

        override fun bind(
            viewHolder: RecyclerView.ViewHolder,
            data: RecordsItem,
            list: List<RecordsItem>
        ) {
            binding.root.let {
            }

        }

    }
    private var onItemClickListenerFactory: ((data: Any, list: List<*>, isLiked: Boolean, viewId: Int, position: Int, _viewBinding: ViewBinding?) -> Unit)? =
        null

    fun setOnItemClickListenerFactory(listener: (data: Any, list: List<*>, isLiked: Boolean, viewId: Int, position: Int, _viewBinding: ViewBinding?) -> Unit) {
        onItemClickListenerFactory = listener
    }

    private var onDeviceItemClickListenerFactory: ((data: Any, list: List<*>, isLiked: Boolean, viewId: Int, position: Int, _viewBinding: ViewBinding?) -> Unit)? =
        null

    fun setOnDeviceItemClickListenerFactory(listener: (data: Any, list: List<*>, isLiked: Boolean, viewId: Int, position: Int, _viewBinding: ViewBinding?) -> Unit) {
        onDeviceItemClickListenerFactory = listener
    }

    private var onContainerClickListenerFactory: ((_viewBinding: ViewBinding?, data: Any, list: List<*>) -> Unit)? =
        null

    fun setOnContainerClickListenerFactory(listener: (_viewBinding: ViewBinding?, data: Any, list: List<*>) -> Unit) {
        onContainerClickListenerFactory = listener
    }


}