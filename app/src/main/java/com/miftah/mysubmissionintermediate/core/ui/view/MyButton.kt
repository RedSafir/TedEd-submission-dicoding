package com.miftah.mysubmissionintermediate.core.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.miftah.mysubmissionintermediate.R

class MyButton : AppCompatButton { // custom view pewarisan button

    private var enabledBackground: Drawable
    private var disabledBackground: Drawable
    private var enabledText: Int

    constructor(context: Context) : super(context) { // dibuat secara programmatic (kode)

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { // dibuat di XML

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { // dibuat di XML dengan style default tertentu

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if(isEnabled) enabledBackground else disabledBackground
        textSize = 12f
        gravity = Gravity.CENTER
        setTextColor(enabledText)
    }

    init {
        enabledText = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_disable) as Drawable
    }
}