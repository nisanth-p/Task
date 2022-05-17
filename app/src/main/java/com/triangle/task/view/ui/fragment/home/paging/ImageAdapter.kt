package com.triangle.task.view.ui.fragment.home.paging

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.triangle.task.data.model.pages.Clicked
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.data.model.pages.ImageStatus
import com.triangle.task.data.utill.SharedPrefKey
import com.triangle.task.databinding.LayoutShowingImageBinding
import com.triangle.task.view.base.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import java.lang.reflect.Type


private const val TAG = "xxxImageAdapter"

@DelicateCoroutinesApi
class ImageAdapter(
    context: Context?,
    viewModel: BaseViewModel,
    diffCallback: DiffUtil.ItemCallback<DataItem>
) :
    PagingDataAdapter<DataItem, ImageViewHolder>(diffCallback) {
    private val baseViewModel by lazy { viewModel }

    val selectImageList = mutableListOf<DataItem>()
    val selectedList = mutableListOf<Clicked>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        return ImageViewHolder(
            selectImageList, selectedList, baseViewModel, LayoutShowingImageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, position)
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
    private var selectImageList: MutableList<DataItem>,
    private var selectedList: MutableList<Clicked>,
    private val baseViewModel: BaseViewModel,
    private val binding: LayoutShowingImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var mapImageStatus: HashMap<String, MutableList<Clicked>>

    fun bind(path: DataItem?, position: Int) {

        path?.let {
            // binding.checkbox.isChecked = false
            selectImageList.clear()
            binding.TVText.text = path.firstName+path.lastName
            binding.IMIcon.load(path.avatar) {
                crossfade(durationMillis = 1000)
                transformations(RoundedCornersTransformation(12.5f))
                transformations(CircleCropTransformation())

            }
            baseViewModel.viewModelScope.coroutineContext.run {
                try {
                    val dataItem = Gson().fromJson(
                        baseViewModel.sharedPref.str(SharedPrefKey.IMAGE_STATUS),
                        ImageStatus::class.java
                    )

                    selectedList = (dataItem.myData as MutableList<Clicked>)
                    selectedList.filter {
                        selectImageList = it.myData as MutableList<DataItem>
                        baseViewModel.imagelist.value = selectImageList
                        if (it.position == position)
                            binding.checkbox.isChecked = true
                       else
                            binding.checkbox.isChecked = selectImageList.contains(path)
                        true
                    }
                } catch (e: Exception) {

                }
            }



            binding.checkbox.setOnClickListener {

                if ((it as CheckBox).isChecked) {
                    selectImageList.add(path)
                    selectedList.add(Clicked(position, true, selectImageList))
                } else {
                    selectImageList.remove(path)
                    selectedList.add(Clicked(position, false, selectImageList))
                }

                Log.d(TAG, "bind: AFTER setOnClickListener => selectedList =$selectedList")
                mapImageStatus = hashMapOf("data" to selectedList)
                val gson = Gson()
                var str = ""
                val gsonType: Type =
                    object : TypeToken<HashMap<*, *>?>() {}.type
                str = gson.toJson(mapImageStatus, gsonType)
                baseViewModel.sharedPref.put(SharedPrefKey.IMAGE_STATUS, str)
                baseViewModel.imagelist.value = selectImageList

            }
        }
    }
}