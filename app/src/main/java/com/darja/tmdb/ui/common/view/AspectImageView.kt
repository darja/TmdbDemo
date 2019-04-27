package com.darja.tmdb.ui.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View.MeasureSpec.EXACTLY
import android.widget.ImageView
import com.darja.tmdb.R

class AspectImageView(context: Context, attrs: AttributeSet): ImageView(context, attrs) {
    private var aspectRatio: Float

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AspectImageView)
        this.aspectRatio = typedArray.getFloat(R.styleable.AspectImageView_aspectRatio, 0f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        when {
            width == 0 -> {
                width = Math.round(height * aspectRatio)
                super.onMeasure(MeasureSpec.makeMeasureSpec(width, EXACTLY), heightMeasureSpec)
            }
            height == 0 -> {
                height = Math.round(width / aspectRatio)
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, EXACTLY))
            }
            else -> super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}