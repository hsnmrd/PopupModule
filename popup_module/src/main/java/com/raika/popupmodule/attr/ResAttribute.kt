package com.raika.popupmodule.attr

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.raika.popupmodule.R

class ResAttribute(val context: Context, private val attr: Int) {

    fun getColor(): Int {
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(attrs)
        val color = typedArray.getColor(0, ContextCompat.getColor(context, R.color.popup_module_black_light))
        typedArray.recycle()
        return color
    }
    
    fun getColorStateList() : ColorStateList {
        return ColorStateList.valueOf(getColor())
    }
    
    fun getDrawable(): Drawable? {
        val attrs = intArrayOf(attr)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

}