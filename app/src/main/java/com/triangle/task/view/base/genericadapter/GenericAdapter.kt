package com.triangle.task.view.base.genericadapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.triangle.task.data.model.RecordsItem


private const val TAG = "yyyGenericAdapter"
abstract class GenericAdapter<T>(
    var listItems: List<*>,
    private var useDiffUtil: Boolean = false,
    private var needAnimation: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {



    var data: T? = null
    var datas: T? = null
    override fun getFilter(): Filter? {
       return filter
    }

    fun setItems(listItems: List<T?>?,useDiff:Boolean = false,runAnimation:Boolean=true) {
        if (listItems != null) {
            this.listItems = listItems
        }
        useDiffUtil=useDiff
        needAnimation=runAnimation

        notifyDataSetChanged()
    }

    private val callback = object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is RecordsItem && newItem is RecordsItem) {
                oldItem.uId == newItem.uId
            } else{
                false
            }

        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem is RecordsItem && newItem is RecordsItem) {
                oldItem == newItem
            } else{
                false
            }

        }
    }
fun getAsyncListDiffer():AsyncListDiffer<T>{
    return AsyncListDiffer(this, callback)
}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false), viewType
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        if (useDiffUtil) {
            Log.i(TAG, "onBindViewHolder() -1:  useDiffUtil =>  $useDiffUtil  holder = $holder, position = $position  list =${getAsyncListDiffer().currentList.size} || currentList == ${getAsyncListDiffer().currentList}")
            data = getAsyncListDiffer().currentList[position]
            (holder as Binder<T>).bind(holder,data as T, list = listItems as List<T>)
        } else {
            Log.i(TAG, "onBindViewHolder() -2:  useDiffUtil =>  $useDiffUtil  holder = $holder, position = $position  list =${listItems.size} || listItems == $listItems")
            (holder as Binder<T>).bind(holder, listItems[position] as T,list = listItems as List<T>)
        }
        if (needAnimation)
          fromLeftToRight(holder.itemView, position);
    }

    override fun getItemCount(): Int {
        return if (useDiffUtil) {
            getAsyncListDiffer().currentList.size
        } else
            listItems.size
    }

    override fun getItemViewType(position: Int): Int {

        return    if (useDiffUtil){
           // Log.i(TAG, "getItemViewType: useDiffUtil =true ")
            getLayoutId(position, getAsyncListDiffer().currentList[position] as T)
        }else
        { //Log.i(TAG, "getItemViewType: useDiffUtil =false ")
            getLayoutId(position, listItems[position] as T)
        }

    }


    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder

    internal interface Binder<T> {

        fun bind(viewHolder:RecyclerView.ViewHolder,data: T,list:List<T>)

    }


    //lifecycle test
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Log.i(TAG, "onDetachedFromRecyclerView: ")

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
       recyclerView.addOnScrollListener( object : RecyclerView.OnScrollListener() {
           override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
               on_attach = false
               super.onScrollStateChanged(recyclerView, newState)
           }
       })
        super.onAttachedToRecyclerView(recyclerView)
        Log.i(TAG, "onAttachedToRecyclerView: $recyclerView")

    }
    var DURATION: Long = 150
    private var on_attach = true



    private  fun fromLeftToRight(itemView: View, i: Int) {
        var ii = i
        if (!on_attach) {
            ii = -1
        }
        val not_first_item = ii == -1
        ii += 1
        itemView.translationX = -300f
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0f)
        val animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animatorTranslateY.startDelay = if (not_first_item) DURATION else ii * DURATION
        animatorTranslateY.duration = (if (not_first_item) 2 else 1) * DURATION
        animatorSet.playTogether(animatorTranslateY, animatorAlpha)
        animatorSet.start()
    }


    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        Log.i(TAG, "onFailedToRecycleView: ")
        return super.onFailedToRecycleView(holder)
       
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        Log.i(TAG, "onViewAttachedToWindow: ")
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.i(TAG, "onViewDetachedFromWindow: ")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        Log.i(TAG, "onViewRecycled: ")
    }

    override fun registerAdapterDataObserver(observer: RecyclerView.AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        Log.i(TAG, "registerAdapterDataObserver: $observer")
        observer.onItemRangeChanged(1,1)
    }

    override fun findRelativeAdapterPositionIn(
        adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>,
        viewHolder: RecyclerView.ViewHolder,
        localPosition: Int
    ): Int {
        Log.i(TAG, "findRelativeAdapterPositionIn: ")
        return super.findRelativeAdapterPositionIn(adapter, viewHolder, localPosition)
    }
    
}