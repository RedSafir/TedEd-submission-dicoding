package com.miftah.mysubmissionintermediate.core.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.miftah.mysubmissionintermediate.R

class EmailEditText : AppCompatEditText{

    private lateinit var clearButtonImage: Drawable

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (!isValidEmail(input) && input.isNotEmpty()) error = context.resources.getString(R.string.err_email)
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

    fun isValidEmail(input : CharSequence) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.resources.getString(R.string.title_email)
    }

}