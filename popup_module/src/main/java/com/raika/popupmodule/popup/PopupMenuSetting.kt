package com.raika.popupmodule.popup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow

internal class PopupMenuSetting(var listener: ((view: View, popupWindow: PopupWindow?, dimPopupWindow: PopupWindow?) -> Int?)) {
    
    private var background: Drawable? = null
    private var popupWindow: PopupWindow? = null
    private var dimPopupWindow: PopupWindow? = null
    
    @SuppressLint("ClickableViewAccessibility")
    fun showPopup(context: Activity, targetView: View, layout: Int, isAutoClick: Boolean = true): PopupWindow? {
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val viewParent = layoutInflater.inflate(layout, null)
        
        popupWindow = PopupWindow(context)
        popupWindow?.contentView = viewParent
        popupWindow?.width = WRAP_CONTENT
        popupWindow?.height = WRAP_CONTENT
        popupWindow?.isFocusable = true
        popupWindow?.setBackgroundDrawable(background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow?.elevation = 25f
        }
    
        if (isAutoClick) {
            targetView.setOnTouchListener { _, event ->
                
                viewParent?.let {
                    val x = event.x.toInt()
                    val y = event.y.toInt()
                    var screenHeight = -1
                    var recyclerHeight = -1
                    
                    listener.invoke(it, popupWindow, dimPopupWindow)?.let { recyclerHeightResult ->
                        recyclerHeight = recyclerHeightResult
                        screenHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            val windowMetrics: WindowMetrics = (context).windowManager.currentWindowMetrics
                            val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                            windowMetrics.bounds.height() - insets.top - insets.bottom
                        } else {
                            val displayMetrics = DisplayMetrics()
                            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
                            displayMetrics.heightPixels
                        }
                        
                    }
                    
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            hideKeyboard(context)
                            if (recyclerHeight + targetView.y + targetView.height > screenHeight - 70) {
                                popupWindow?.showAsDropDown(targetView, (x), (y - targetView.height - recyclerHeight))
                            } else {
                                popupWindow?.showAsDropDown(targetView, (x), (y - targetView.height))
                            }
                        }
                        else -> {}
                    }
                }
                false
            }
            targetView.performClick()
            
        } else {
            
            viewParent?.let {
                val x = targetView.x.toInt() + targetView.width / 2
                val y = targetView.y.toInt() + targetView.height / 2
                var screenHeight = -1
                var recyclerHeight = -1
                
                listener.invoke(it, popupWindow, dimPopupWindow)?.let { recyclerHeightResult ->
                    recyclerHeight = recyclerHeightResult
                    screenHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val windowMetrics: WindowMetrics = (context).windowManager.currentWindowMetrics
                        val insets: Insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
                        windowMetrics.bounds.height() - insets.top - insets.bottom
                    } else {
                        val displayMetrics = DisplayMetrics()
                        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
                        displayMetrics.heightPixels
                    }
                }
                hideKeyboard(context)
    
                if (recyclerHeight + targetView.y + targetView.height > screenHeight - 70) {
                    popupWindow?.showAsDropDown(targetView, (x), (y - targetView.height - recyclerHeight))
                } else {
                    popupWindow?.showAsDropDown(targetView, (x), (y - targetView.height - recyclerHeight))
                }
            }
        }
        
        return popupWindow
    }
    
    fun setBackground(drawable: Drawable?): PopupMenuSetting {
        this.background = drawable
        return this
    }
    
    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    
}