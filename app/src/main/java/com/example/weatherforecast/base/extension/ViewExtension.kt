package com.example.weatherforecast.base.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherforecast.R

internal fun View.show() {
    this.visibility = View.VISIBLE
}

internal fun View.dismiss() {
    this.visibility = View.GONE
}

internal fun View.invisible() {
    this.visibility = View.INVISIBLE
}

internal fun ImageView.loadImageUrl(
    imageUrl : String
) {
    Glide.with(context)
        .load(imageUrl)
        .error(R.color.black)
        .into(this)
}

internal fun View.hideKeyboardFrom(context: Context) {
    val imm: InputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    this.clearFocus()
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

internal fun FragmentActivity.onBackPress() {
     this.onBackPressedDispatcher.onBackPressed()
}

internal fun RecyclerView.init() {
    this.setHasFixedSize(true)
    val layoutManager: RecyclerView.LayoutManager =
        androidx.recyclerview.widget.LinearLayoutManager(this.context)
    this.layoutManager = layoutManager
}
