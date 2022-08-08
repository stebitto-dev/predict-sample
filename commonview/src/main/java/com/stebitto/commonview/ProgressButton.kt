package com.stebitto.commonview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView

class ProgressButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val rootLayout: MaterialCardView
    private val progressBar: ProgressBar
    private val textView: TextView

    init {
        val root = LayoutInflater
            .from(context)
            .inflate(R.layout.view_progress_button, this, true)

        rootLayout = root.findViewById(R.id.root_layout)
        progressBar = root.findViewById(R.id.progress_button_bar)
        textView = root.findViewById(R.id.progress_button_text)

        loadAttrs(attrs, defStyleAttr)
    }

    private fun loadAttrs(attrs: AttributeSet?, defStyleAttr: Int) {
        val arr = context.obtainStyledAttributes(
            attrs, R.styleable.ProgressButton, defStyleAttr, 0
        )

        val text = arr.getString(R.styleable.ProgressButton_text)
        val loading = arr.getBoolean(R.styleable.ProgressButton_loading, false)
        val enabled = arr.getBoolean(R.styleable.ProgressButton_enabled, true)
        arr.recycle()
        setText(text)
        setLoading(loading)
        isEnabled = enabled
    }

    fun setLoading(loading: Boolean) {
        isClickable = !loading
        progressBar.isVisible = loading
        textView.isVisible = !loading
    }

    fun setText(text: String?) {
        textView.text = text
        invalidate()
        requestLayout()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        rootLayout.isEnabled = enabled
    }
}