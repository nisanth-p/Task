package com.triangle.task.view.ui.fragment.home.paging

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.databinding.LayoutShowingImageBinding
import com.triangle.task.view.base.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

private const val TAG = "xxxImageAdapter"
@DelicateCoroutinesApi
class ImageAdapter(context: Context?,viewModel: BaseViewModel ,diffCallback: DiffUtil.ItemCallback<DataItem>) :
  PagingDataAdapter<DataItem, ImageViewHolder>(diffCallback) {

init {

}
  private val baseViewModel by lazy { viewModel }

  val selectImageList = mutableListOf<DataItem>()
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ImageViewHolder {
    return ImageViewHolder(selectImageList, baseViewModel,LayoutShowingImageBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    ))
  }

  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    val item = getItem(position)
    if (item != null) {
      Log.d(TAG, "onBindViewHolder: ${item.email}")
    }
    holder.bind(item)
  }
  companion object {
    val ImageDiffCallBack = object : DiffUtil.ItemCallback<DataItem>() {
      override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
      }
    }
  }
}


@DelicateCoroutinesApi
class ImageViewHolder(
  private val selectImageList: MutableList<DataItem>,
  private val baseViewModel:BaseViewModel,
  private val binding: LayoutShowingImageBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(path: DataItem?) {
    path?.let {
      Log.d(TAG, "bind: ")
      binding.TVText.text = path.email
      binding.IMIcon.load(path.avatar) {
        crossfade(durationMillis = 1500)
        transformations(RoundedCornersTransformation(12.5f))

      }
      binding.checkbox.setOnClickListener {
        if ((it as CheckBox).isChecked)
          selectImageList.add(path)
        else
          selectImageList.remove(path)
        Log.d(TAG, "bind: "+path.firstName)
        Log.d(TAG, "bind: selectImageList ="+selectImageList.toString())
        baseViewModel.imagelist.value = selectImageList
      }
    }
  }
}