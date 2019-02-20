package me.carlamko.payup.model

import android.widget.Toast

sealed class UIEvent {
    class ShowToast(val text: String, val length: Int = Toast.LENGTH_LONG) : UIEvent()
}