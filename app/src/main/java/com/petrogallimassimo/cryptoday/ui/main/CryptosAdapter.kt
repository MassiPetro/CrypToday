package com.petrogallimassimo.cryptoday.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petrogallimassimo.cryptoday.databinding.ItemCryptoBinding
import com.petrogallimassimo.cryptoday.domain.model.CryptoUIModel

class CryptosAdapter(
    private val context: Context,
    private val onClick: (CryptoUIModel) -> Unit
) :
    ListAdapter<CryptoUIModel, CryptosAdapter.CryptoViewHolder>(
        CryptoDiff
    ) {

    private var listCryptos = ArrayList<CryptoUIModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        return CryptoViewHolder(
            ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val cryptoPosition = listCryptos[position]
        holder.bind(cryptoPosition)

    }

    override fun getItemCount(): Int {
        return listCryptos.size
    }

    fun replaceItems(list: List<CryptoUIModel>) {
        listCryptos.clear()
        listCryptos.addAll(list)
        notifyDataSetChanged()
    }

    inner class CryptoViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CryptoUIModel) {
            with(binding) {
                image.let {
                    Glide.with(context)
                        .load(item.image)
                        .into(it)
                }
                name.text = item.name
                val currentPrice = item.currentPrice.toString() + "€"
                eurValue.text = currentPrice
                itemLayout.setOnClickListener {
                    onClick(listCryptos[adapterPosition])
                }
            }
        }
    }

    private object CryptoDiff : DiffUtil.ItemCallback<CryptoUIModel>() {
        override fun areItemsTheSame(
            oldItem: CryptoUIModel,
            newItem: CryptoUIModel
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CryptoUIModel,
            newItem: CryptoUIModel
        ): Boolean =
            oldItem.equals(newItem)

    }
}