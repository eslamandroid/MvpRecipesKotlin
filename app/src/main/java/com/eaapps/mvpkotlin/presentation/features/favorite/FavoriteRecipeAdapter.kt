package com.eaapps.mvpkotlin.presentation.features.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eaapps.mvpkotlin.R
import com.eaapps.mvpkotlin.data.entity.Recipe
import com.eaapps.mvpkotlin.databinding.ListitemRecipeBinding

class FavoriteRecipeAdapter(private var items: MutableList<Recipe>, private val listener: Listener) :
    RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ListitemRecipeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    fun remove(recipeIndex:Int){
        items.removeAt(recipeIndex)
        notifyItemRemoved(recipeIndex)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: ListitemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Recipe, listen: Listener) {
            Glide.with(binding.root.context).load(item.image_url)
                .into(binding.imageView)

            binding.title.text = item.title

            binding.root.setOnClickListener {
                listen.onClickItem(item)
            }

            if (item.favorited)
                binding.favButton.setImageResource(R.drawable.ic_favorite_24dp)
            else
                binding.favButton.setImageResource(R.drawable.ic_favorite_border_24dp)

            binding.favButton.setOnClickListener {
                if (item.favorited)
                    listen.onRemoveFavorite(item)

            }

        }


    }

    interface Listener {
        fun onClickItem(item: Recipe)
         fun onRemoveFavorite(item: Recipe)
    }

}