package me.carlamko.payup.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.frag_payout.*
import me.carlamko.payup.Application.Companion.injector
import me.carlamko.payup.R
import me.carlamko.payup.extensions.formatAsMoney

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
            tv_total.text = 0f.formatAsMoney()
            itemSummaryAdapter.refresh()

            Toast.makeText(requireContext(), "Paid out!", Toast.LENGTH_LONG).show()
        }

        rv_item_summary.layoutManager = LinearLayoutManager(requireContext())
        rv_item_summary.adapter = itemSummaryAdapter
    }

}