package me.carlamko.payup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_summary.view.*
import me.carlamko.payup.Application.Companion.injector
import me.carlamko.payup.R
import me.carlamko.payup.extensions.formatAsMoney
import me.carlamko.payup.model.DataChangeEvent
import me.carlamko.payup.model.ItemData
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ItemSummaryAdapter : RecyclerView.Adapter<ItemSummaryAdapter.ItemSummaryViewHolder>() {
    private val dataItems = injector.itemData()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSummaryViewHolder =
        ItemSummaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_summary, parent, false))

    override fun onBindViewHolder(holder: ItemSummaryViewHolder, position: Int) {
        holder.populate(dataItems[position])
    }

    override fun getItemCount(): Int = dataItems.size

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataUpdate(event: DataChangeEvent) {
        notifyDataSetChanged()
    }

    class ItemSummaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populate(itemData: ItemData) {
            val transactionManager = injector.transactionManager()

            itemView.iv_item.setImageResource(itemData.iconRes)

            val quantity = transactionManager.getItemQuantity(itemData)
            itemView.tv_item_quantity.text = "x $quantity"
            itemView.tv_item_total.text = " = ${(quantity * itemData.price).formatAsMoney()}"

            itemView.iv_item_increment.setOnClickListener {
                transactionManager.incrementItem(itemData)
            }

            itemView.iv_item_decrement.setOnClickListener {
                transactionManager.decrementItem(itemData)
            }
        }
    }
}