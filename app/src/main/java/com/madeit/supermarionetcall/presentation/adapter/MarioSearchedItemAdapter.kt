package com.madeit.supermarionetcall.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madeit.supermarionetcall.data.model.Mario
import com.madeit.supermarionetcall.databinding.ItemAllMarioBinding
import com.madeit.supermarionetcall.databinding.ItemSearchLayoutBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MarioSearchedItemAdapter :
    RecyclerView.Adapter<MarioSearchedItemAdapter.SearchedItemViewHolder>() {

    private var marioList = listOf<Mario>()

    fun clear(){
        val emptyList = emptyList<Mario>()
        val marioCallBack = SearchedMarioCallBack(marioList, emptyList)
        val diffResult = DiffUtil.calculateDiff(marioCallBack)
        marioList = emptyList //
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateMariosList(newMarioList: List<Mario>) {
        val marioCallBack = SearchedMarioCallBack(marioList, newMarioList)
        val diffResult = DiffUtil.calculateDiff(marioCallBack)
        marioList = newMarioList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedItemViewHolder {
        val view = ItemSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchedItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchedItemViewHolder, position: Int) {
        holder.bind(mario = marioList[position])
    }

    override fun getItemCount(): Int = marioList.size

    class SearchedMarioCallBack(
        private val oldList: List<Mario>,
        private val newList: List<Mario>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oList = oldList[oldItemPosition]
            val nList = newList[newItemPosition]
            return oList.id == nList.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oList = oldList[oldItemPosition]
            val nList = newList[newItemPosition]
            return oList == nList
        }
    }

    inner class SearchedItemViewHolder(private val binding: ItemSearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mario: Mario) = with(binding) {
            Glide.with(root)
                .load(mario.image)
                .into(itemImage)
            tvName.text = mario.name
//            tvCharacter.text = mario.character
//            tvMarioSeries.text = mario.marioSeries
//            tvGameSeries.text = mario.gameSeries
//            tvType.text = mario.type
//            tvRelease.text = mario.release?.na?.let { naReleaseDate ->
//                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
//                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(naReleaseDate)
//                )
//            } ?: "Release date not available"
        }
    }
}