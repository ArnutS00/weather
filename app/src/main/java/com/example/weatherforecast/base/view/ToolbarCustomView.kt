package com.example.weatherforecast.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.weatherforecast.R
import com.example.weatherforecast.base.extension.dismiss
import com.example.weatherforecast.base.extension.show
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_INT
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING

class ToolbarCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    lateinit var back: AppCompatImageView
    lateinit var close: AppCompatImageView
    private lateinit var textTitle: AppCompatTextView
    private lateinit var toolbarLayout: ConstraintLayout

    init {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet? = null) {
        val binding = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this)
        back = binding.findViewById(R.id.ivBack)
        close = binding.findViewById(R.id.ivClose)
        textTitle = binding.findViewById(R.id.tvTitlePage)
        toolbarLayout = binding.findViewById(R.id.clTopTitle)

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ToolbarCustomView,
            0, 0
        ).apply {
            try {
                val titleRes =
                    this.getResourceId(R.styleable.ToolbarCustomView_toolbarTitle,
                        DEFAULT_INT
                    )
                val isVisibleBackIcon = this.getInt(R.styleable.ToolbarCustomView_isVisibleToolbarBack, 0)
                val isVisibleToolbarClose = this.getInt(R.styleable.ToolbarCustomView_isVisibleToolbarClose, 1)

                takeIf { titleRes != DEFAULT_INT }?.let {
                    textTitle.text = context.getString(titleRes)
                } ?: run {
                    textTitle.text = DEFAULT_STRING
                }

                takeIf { isVisibleBackIcon != 1 }?.let {
                    back.show()
                } ?: run {
                    back.dismiss()
                }

                takeIf { isVisibleToolbarClose != 1 }?.let {
                    close.show()
                } ?: run {
                    close.dismiss()
                }
            } finally {
                recycle()
            }
        }
    }

    fun setTitle(title: String) {
        textTitle.text = title
    }

    fun setVisibleTitle(isVisible: Boolean) {
        textTitle.isVisible = isVisible
    }
}