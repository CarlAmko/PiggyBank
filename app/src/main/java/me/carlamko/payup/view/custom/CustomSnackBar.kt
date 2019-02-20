package me.carlamko.payup.view.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import me.carlamko.payup.R

class CustomSnackBar(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) :
    BaseTransientBottomBar<CustomSnackBar>(parent, content, contentViewCallback) {

    companion object {
        /*fun make(view: View, text: String, length: Int): CustomSnackBar =
            CustomSnackBar(
                findSuitableParent(view),
                LayoutInflater.from(view.context).inflate(R.layout.custom_snackbar, null, false),
                null)*/

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

}