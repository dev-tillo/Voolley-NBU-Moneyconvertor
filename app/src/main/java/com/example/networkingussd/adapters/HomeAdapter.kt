package com.example.networkingussd.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkingussd.R
import com.example.networkingussd.classes.MoneyItem
import com.example.networkingussd.databinding.ItemHomeBinding
import com.squareup.picasso.Picasso

class HomeAdapter(var onitemCliked: OnitemCliked) :
    ListAdapter<MoneyItem, HomeAdapter.Vh>(MyDiffUtill()) {

    inner class Vh(var itemHomeBinding: ItemHomeBinding) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(money: MoneyItem, position: Int) {
            itemHomeBinding.apply {
                name.text = money.Ccy
                valyutaname.text = money.CcyNm_UZ
                sinonim.text = money.Rate
                daymonthyear.text = money.Date + "/"
                time.text = money.Diff

                Picasso.get().load(money.image).into(image)
//                Glide.with().load(money.image)
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                image.setImageURI(Uri.parse(person?.image))

                card.setOnClickListener {
                    onitemCliked.cardClick(money, position)
                }
                blur.setOnClickListener {
                    onitemCliked.onCliked(money, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class MyDiffUtill : DiffUtil.ItemCallback<MoneyItem>() {
        override fun areItemsTheSame(oldItem: MoneyItem, newItem: MoneyItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MoneyItem, newItem: MoneyItem): Boolean {
            return oldItem == newItem
        }
    }

    interface OnitemCliked {
        fun onCliked(money: MoneyItem, position: Int)
        fun cardClick(money: MoneyItem, position: Int)
    }
}