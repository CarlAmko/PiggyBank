package me.carlamko.payup.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import me.carlamko.payup.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_pay.setOnClickListener {

        }

        // setup recycler view items
        rv_pay_items.layoutManager = LinearLayoutManager(this)
        rv_pay_items.adapter = PayItemAdapter()
    }


}
