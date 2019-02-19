package me.carlamko.payup.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.pay_item.view.*
import me.carlamko.payup.Application.Companion.injector
import me.carlamko.payup.R
import me.carlamko.payup.model.ItemData

class PayItemAdapter : RecyclerView.Adapter<PayItemAdapter.PayItemViewHolder>() {
    // @TODO un-hardcode this
    var items: List<ItemData> = listOf(
        ItemData("Chocolate", R.drawable.chocolate, 1.5f),
        ItemData("Pastry", R.drawable.pastry, 1.5f),
        ItemData("Candy", R.drawable.candy, 1.5f),
        ItemData("Coffee", R.drawable.coffee, 1.5f))

        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayItemViewHolder =
        PayItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pay_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PayItemViewHolder, position: Int) {
        holder.setData(items[position])
    }

    override fun getItemCount(): Int = items.size

    class PayItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(itemData: ItemData) {
            itemView.tv_item_name.text = itemData.name
            itemView.iv_item_icon.setImageResource(itemData.icon)
            itemView.tv_item_price.text = "$${"%.2f".format(itemData.price)}"

            itemView.cl_pay_item.setOnClickListener {
                injector.settings().addToTotal(itemData.price)
            }
        }
    }
}