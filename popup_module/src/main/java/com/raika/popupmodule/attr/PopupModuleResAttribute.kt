package com.raika.popupmodule.attr

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.raika.popupmodule.R

class PopupModuleResAttribute(private val context: Context, private val attr: Int) {

    fun getDrawable(): Drawable? {
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

}