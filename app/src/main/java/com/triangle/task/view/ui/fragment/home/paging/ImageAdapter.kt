package com.triangle.task.view.ui.fragment.home.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.UserPages
import com.triangle.task.databinding.LayoutShowingImageBinding

private const val TAG = "ImageAdapter"
class ImageAdapter(diffCallback: DiffUtil.ItemCallback<DataItem>) :
  PagingDataAdapter<DataItem, ImageViewHolder>(diffCallback) {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ImageViewHolder {
    return ImageViewHolder( LayoutShowingImageBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    ))
  }

  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    val item = getItem(position)
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



class ImageViewHolder(
  private val binding: LayoutShowingImageBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(path: DataItem?) {
    path?.let {
      Log.d(TAG, "bind: ")
      binding.TVText.text = path.email
      binding.IMIcon.load("https://image.tmdb.org/t/p/w500/${path.avatar}") {
        crossfade(durationMillis = 1500)
        transformations(RoundedCornersTransformation(12.5f))
      }
    }
  }
}