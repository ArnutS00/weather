package com.example.weatherforecast.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    val context: Context
        @JvmName("getNonNullContext")
        get() = requireContext()

    val activity: FragmentActivity
        @JvmName("getNonNullActivity")
        get() = requireActivity()

}