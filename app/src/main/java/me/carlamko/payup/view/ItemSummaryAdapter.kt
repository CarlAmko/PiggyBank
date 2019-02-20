package me.carlamko.payup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_summary.view.*
import me.carlamko.payup.Application.Companion.injector
import me.carlamko.payup.R
import me.carlamko.payup.extensions.formatAsMoney
import me.carlamko.payup.model.ItemData

class ItemSummaryAdapter : RecyclerView.Adapter<ItemSummaryAdapter.ItemSummaryViewHolder>() {
    private val dataItems = injector.itemData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSummaryViewHolder =
        ItemSummaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_summary, parent, false))

    override fun onBindViewHolder(holder: ItemSummaryViewHolder, position: Int) {
        holder.populate(dataItems[position])
    }

    override fun getItemCount(): Int = dataItems.size

    fun refresh() {
        notifyDataSetChanged()
    }

    class ItemSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun populate(itemData: ItemData) {
            itemView.iv_item.setImageResource(itemData.iconRes)

            val quantity = injector.transactionManager().getItemQuantity(itemData)
            itemView.tv_item_quantity.text = "x $quantity"
            itemView.tv_item_total.text = " = ${(quantity * itemData.price).formatAsMoney()}"
        }
    }
}