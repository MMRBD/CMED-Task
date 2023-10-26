package com.mmrbd.cmedtask.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.mmrbd.cmedtask.R
import com.mmrbd.cmedtask.data.model.CharacterModelItem
import com.mmrbd.cmedtask.databinding.ItemCharacterBinding

class CharacterAdapter(private val onItemClickListener: (CharacterModelItem) -> Unit) :
    Adapter<CharacterAdapter.CharacterViewModel>() {

    private var characterData: List<CharacterModelItem> = emptyList()

    inner class CharacterViewModel(private val binding: ItemCharacterBinding) :
        ViewHolder(binding.root) {

        fun bindData(characterModelItem: CharacterModelItem) {
            with(binding) {
                imgCharacter.load(
                    characterModelItem.image
                ) {
                    transformations(CircleCropTransformation())
                    crossfade(true)
                    placeholder(R.drawable.no_image)
                    error(R.drawable.no_image)
                }
                tvName.text = characterModelItem.name
                tvActorName.text = characterModelItem.actor
                tvHouseName.text = characterModelItem.house
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewModel {
        return CharacterViewModel(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun submitCharacterData(characterData: List<CharacterModelItem>) {
        this.characterData = characterData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = characterData.size

    override
    fun onBindViewHolder(holder: CharacterViewModel, position: Int) {
        holder.bindData(characterData[position])

        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(characterData[position])
        }
    }
}