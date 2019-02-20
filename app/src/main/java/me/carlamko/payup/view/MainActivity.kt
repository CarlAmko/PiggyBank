package me.carlamko.payup.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.carlamko.payup.R
import me.carlamko.payup.model.UIEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    private val payoutFragment = PayoutFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        // launch payout fragment
        fab_pay.setOnClickListener {
            if (!isPayoutVisible()) {
                supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, payoutFragment, payoutFragment.javaClass.name)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit()
            }
        }

        // setup recycler view items
        rv_pay_items.layoutManager = LinearLayoutManager(this)
        rv_pay_items.adapter = PayItemAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onBackPressed() {
        if (isPayoutVisible()) {
            supportFragmentManager.beginTransaction()
                .remove(payoutFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        } else {
            super.onBackPressed()
        }
    }

    private fun isPayoutVisible(): Boolean =
        supportFragmentManager.findFragmentByTag(payoutFragment.javaClass.name) != null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(uiEvent: UIEvent) {
        if (uiEvent is UIEvent.ShowToast) {
            Toast.makeText(this, uiEvent.text, uiEvent.length).show()
        }
    }
}
