package me.carlamko.payup.view.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.android.synthetic.main.custom_snackbar.view.*
import me.carlamko.payup.R

class CustomSnackBar(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) :
    BaseTransientBottomBar<CustomSnackBar>(parent, content, contentViewCallback) {

    companion object {
        fun make(view: View, text: String, iconRes: Int, length: Int): CustomSnackBar {
            val parent = findSuitableParent(view)
            val snackbarView = LayoutInflater.from(view.context).inflate(R.layout.custom_snackbar, parent, false)
            return CustomSnackBar(
                parent,
                snackbarView,
                CustomContentViewCallback()
            ).apply {
                snackbarView.iv_snackbar.setImageResource(iconRes)
                snackbarView.tv_snackbar.text = text
                snackbarView.b_snackbar.setOnClickListener { dismiss() }

                // remove parent layout padding
                this.view.setPadding(0, 0, 0, 0)
            }
        }

        private fun findSuitableParent(v: View): ViewGroup {
            var view: View? = v
            do {
                if (view is CoordinatorLayout) {
                    return view
                } else if (view is FrameLayout) {
                    if (view.getId() == android.R.id.content) {
                        return view
                    }
                }
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                if (view != null) {
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            throw IllegalArgumentException("No suitable view found.")
        }
    }

    class CustomContentViewCallback : ContentViewCallback {
        override fun animateContentOut(delay: Int, duration: Int) {
        }

        override fun animateContentIn(delay: Int, duration: Int) {
        }
    }

    fun setAction(text: String, action: () -> Unit) {
        view.b_snackbar.text = text
        view.b_snackbar.setOnClickListener {
            action.invoke()
            dismiss()
        }
    }
}