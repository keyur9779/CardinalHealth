package com.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.data.local.entity.AlbumEntity
import com.test.R
import com.test.databinding.ItemListBinding
import com.view.adapter.AlbumListAdapter.ArticleViewHolder
import com.view.base.BaseAdapter
import com.view.callbacks.AlbumListCallback
import java.util.*

/**
 * This class represents the album list recyclerview adapter

 */
class AlbumListAdapter(articleListCallback: AlbumListCallback) : BaseAdapter<ArticleViewHolder?, AlbumEntity>(), Filterable {
    private var albumEntities: List<AlbumEntity>
    private var albumEntitiesFiltered: List<AlbumEntity>
    private val albumListCallback: AlbumListCallback
    override fun setData(entities: List<AlbumEntity>) {
        albumEntities = entities
        albumEntitiesFiltered = entities
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ArticleViewHolder {
        return ArticleViewHolder.create(LayoutInflater.from(viewGroup.context), viewGroup, albumListCallback)
    }

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, i: Int) {
        viewHolder.onBind(albumEntitiesFiltered[i])
    }

    override fun getItemCount(): Int {
        return albumEntitiesFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                albumEntitiesFiltered = if (charString.isEmpty()) {
                    albumEntities
                } else {
                    val filteredList: MutableList<AlbumEntity> = ArrayList()
                    for (row in albumEntities) {

                        if (row.title.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = albumEntitiesFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                albumEntitiesFiltered = filterResults.values as ArrayList<AlbumEntity>
                notifyDataSetChanged()
            }
        }
    }

    class ArticleViewHolder private constructor(private val binding: ItemListBinding, callback: AlbumListCallback) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(albumDataEntity: AlbumEntity) {


            binding.article = albumDataEntity
            binding.executePendingBindings()

            Glide
                    .with(binding.imageView)
                    .load(albumDataEntity.url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_place_holder)
                    .into(binding.imageView)
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, callback: AlbumListCallback): ArticleViewHolder {
                val itemMovieListBinding = ItemListBinding.inflate(inflater, parent, false)
                return ArticleViewHolder(itemMovieListBinding, callback)
            }
        }

        init {
            binding.root.setOnClickListener { v: View? -> callback.onAlbumClicked(binding.article!!) }
        }
    }

    init {
        albumEntities = ArrayList()
        albumEntitiesFiltered = ArrayList()
        this.albumListCallback = articleListCallback
    }
}