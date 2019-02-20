package me.carlamko.payup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_payout.*
import me.carlamko.payup.Application.Companion.injector
import me.carlamko.payup.R
import me.carlamko.payup.extensions.formatAsMoney
import me.carlamko.payup.model.UIEvent
import org.greenrobot.eventbus.EventBus

class PayoutFragment : Fragment() {
    private val itemSummaryAdapter = ItemSummaryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.frag_payout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // trigger onBackPressed
        iv_back.setOnClickListener {
            requireActivity().onBackPressed()
        }

        tv_total.text = injector.transactionManager().getBalance().formatAsMoney()

        // trigger settle up
        tv_pay.setOnClickListener {
            injector.transactionManager().settleUp()
            onUpdate()

            // allow option to undo
            EventBus.getDefault().post(UIEvent.ShowSnackBar("Paid Out!", actionText = "Undo?", action = {
                injector.transactionManager().undo()
                onUpdate()
            }))
        }

        rv_item_summary.layoutManager = LinearLayoutManager(requireContext())
        rv_item_summary.adapter = itemSummaryAdapter
    }

    private fun onUpdate() {
        itemSummaryAdapter.refresh()
        tv_total.text = injector.transactionManager().getBalance().formatAsMoney()
    }

}