package com.binar.melif.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.binar.melif.BuildConfig
import com.binar.melif.databinding.ItemHeaderTvShowBinding
import com.binar.melif.databinding.ItemSectionTvShowBinding
import com.binar.melif.uimodel.TV_SHOW_TYPE_HEADER
import com.binar.melif.uimodel.TvShowItem

class TvShowAdapter(var listener: ((TvShowItem) -> Unit)? = null)
    : RecyclerView.Adapter<ViewHolder>() {

    val data = mutableListOf<TvShowItem>()

    fun setItems(newData: List<TvShowItem>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == TV_SHOW_TYPE_HEADER) {
            HomeHeaderItemViewHolder(
                ItemHeaderTvShowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            HomeSectionItemViewHolder(
                ItemSectionTvShowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HomeHeaderItemViewHolder)
            holder.bind(data[position] as TvShowItem.TvShowHeaderItem)
        else if (holder is HomeSectionItemViewHolder)
            holder.bind(data[position] as TvShowItem.TvShowSectionItem)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }
}


class HomeHeaderItemViewHolder(private val binding: ItemHeaderTvShowBinding) :
    ViewHolder(binding.root) {

    fun bind(item: TvShowItem.TvShowHeaderItem) {
        binding.ivHeaderTvshow.load(BuildConfig.BASE_POSTER_IMG_URL + item.data?.posterPath)
        binding.tvTitleTvshow.text = item.data?.name
    }

}

class HomeSectionItemViewHolder(private val binding: ItemSectionTvShowBinding) :
    ViewHolder(binding.root) {

    private val adapter: TvShowListAdapter by lazy {
        TvShowListAdapter {
           // AnimeDetailActivity.startActivity(itemView.context, it.animeId)
        }
    }

    fun bind(item: TvShowItem.TvShowSectionItem) {
        binding.tvTitleSection.text = itemView.context.getText(item.sectionName)
        binding.rvContents.adapter = adapter
        adapter.setItems(item.data)
    }

}