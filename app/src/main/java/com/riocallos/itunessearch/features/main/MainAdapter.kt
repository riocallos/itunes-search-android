package com.riocallos.itunessearch.features.main

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.riocallos.itunessearch.R
import com.riocallos.itunessearch.base.BaseAdapter
import com.riocallos.itunessearch.base.BaseViewHolder
import com.riocallos.itunessearch.databinding.ItemSearchResultBinding
import com.riocallos.itunessearch.domain.models.SearchResult

class MainAdapter constructor(context: Context) : BaseAdapter<SearchResult>(context, BaseDiffCallback()) {

    internal inner class ItemSearchResultViewHolder(private val binding: ItemSearchResultBinding) :
            BaseViewHolder<SearchResult>(binding.root) {
        override fun bind(item: SearchResult, position: Int) {
            binding.searchResult = item
            binding.executePendingBindings()
        }
    }

    override fun createItemHolder(viewGroup: ViewGroup, itemType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemSearchResultBinding>(
                layoutInflater,
                R.layout.item_search_result,
                viewGroup,
                false
        )
        return ItemSearchResultViewHolder(binding)
    }

    override fun bindItemViewHolder(viewHolder: RecyclerView.ViewHolder, data: SearchResult, position: Int) {
        (viewHolder as ItemSearchResultViewHolder)
                .bind(
                        data,
                        position
                )
    }
}