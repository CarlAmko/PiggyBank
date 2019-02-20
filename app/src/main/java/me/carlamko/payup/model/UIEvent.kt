package me.carlamko.payup.model

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import me.carlamko.payup.R

sealed class UIEvent {
    class ShowToast(val text: String, val length: Int = Toast.LENGTH_LONG) : UIEvent()
    class ShowSnackBar(
        val text: String,
        val iconRes: Int = R.drawable.ic_circle_check,
        val length: Int = Snackbar.LENGTH_LONG,
        val actionText: String? = null,
        val action: (() -> Unit)? = null
    ) : UIEvent()
}