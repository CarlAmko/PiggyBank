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
import me.carlamko.payup.model.DataChangeEvent
import me.carlamko.payup.model.UIEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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

            // allow option to undo
            EventBus.getDefault().post(UIEvent.ShowSnackBar("Paid Out!", actionText = "Undo?", action = {
                injector.transactionManager().undo()
            }))
        }

        rv_item_summary.layoutManager = LinearLayoutManager(requireContext())
        rv_item_summary.adapter = itemSummaryAdapter
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataChangeEvent(event: DataChangeEvent) {
        tv_total.text = injector.transactionManager().getBalance().formatAsMoney()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        EventBus.getDefault().register(itemSummaryAdapter)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
        EventBus.getDefault().unregister(itemSummaryAdapter)
    }
}