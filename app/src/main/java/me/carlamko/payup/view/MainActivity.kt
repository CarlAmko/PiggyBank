package me.carlamko.payup.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.carlamko.payup.R
import me.carlamko.payup.model.UIEvent
import me.carlamko.payup.view.custom.CustomSnackBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)

        // launch payout fragment
        fab_pay.setOnClickListener {
            togglePayout(true)
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
            togglePayout(false)
        } else {
            super.onBackPressed()
        }
    }

    private fun togglePayout(show: Boolean) {
        when (show) {
            true -> {
                if (!isPayoutVisible()) {
                    supportFragmentManager.beginTransaction()
                        .add(android.R.id.content, PayoutFragment(), PayoutFragment::class.java.name)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
            }
            false -> {
                if (isPayoutVisible()) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentByTag(PayoutFragment::class.java.name)!!)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit()
                }
            }
        }
    }

    private fun isPayoutVisible(): Boolean =
        supportFragmentManager.findFragmentByTag(PayoutFragment::class.java.name) != null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.ShowToast -> Toast.makeText(this, uiEvent.text, uiEvent.length).show()
            is UIEvent.ShowSnackBar -> CustomSnackBar.make(
                findViewById(android.R.id.content),
                uiEvent.text,
                uiEvent.iconRes,
                uiEvent.length
            ).apply {
                if (uiEvent.actionText != null && uiEvent.action != null) {
                    setAction(uiEvent.actionText, uiEvent.action)
                }
            }.show()
        }
    }
}
