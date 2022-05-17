package com.triangle.task.view.ui.fragment.select.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.triangle.task.data.model.pages.DataItem
import com.triangle.task.databinding.LayoutSelectedImageBinding
import javax.inject.Inject


class SelectAdapter @Inject constructor(list: List<DataItem>):
    RecyclerView.Adapter<SelectAdapter.MyViewHolder>() {
    inner class MyViewHolder(private val binding: LayoutSelectedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(roomItem: DataItem) {
            binding.TVText.text = "ID : "+roomItem.id.toString()
            binding.TVName.text = "Name : "+roomItem.firstName+roomItem.lastName
            binding.TVEmail.text = "Email : "+roomItem.email.toString()
            binding.IMIcon.load(roomItem.avatar) {
                crossfade(durationMillis = 1500)
                transformations(RoundedCornersTransformation(12.5f))
                transformations(CircleCropTransformation())
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectAdapter.MyViewHolder {
       return MyViewHolder(LayoutSelectedImageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: SelectAdapter.MyViewHolder, position: Int) {
        val data = differ.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: MutableList<DataItem>)
    {
        differ.submitList(list)
    }
    private val callback = object : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
}