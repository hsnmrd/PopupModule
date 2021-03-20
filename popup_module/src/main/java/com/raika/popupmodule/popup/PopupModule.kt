package com.raika.popupmodule.popup

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raika.popupmodule.R
import com.raika.popupmodule.attr.PopupModuleResAttribute

class PopupMenuRoot {
    companion object {
        var popup: PopupWindow? = null
    }
}

data class TaskMenuModel(
    var item: MenuModel, var adapter: MenuAdapter?, var position: Int,
)

data class ViewCreateModel(var view: View, var popupWindow: PopupWindow?, var dimPopupWindow: PopupWindow?)


fun Context.showPopupOnTouch(
    targetView: View,
    layout: Int,
    onViewCreate: (ViewCreateModel) -> Unit,
) {
    PopupMenuRoot.popup?.dismiss()
    PopupMenuRoot.popup = PopupMenuSetting { view, popupWindow, dimPopupWindow ->
        onViewCreate(ViewCreateModel(view, popupWindow, dimPopupWindow))
        view.rootView.height
    }
            .setBackground(PopupModuleResAttribute(this, R.attr.popup_module_dr_spinner).getDrawable())
            .showPopup(this as Activity, targetView, layout)
}

fun Context.showPopupMenuOnTouch(
    targetView: View,
    dataList: MutableList<MenuModel> = emptyArray<MenuModel>().toMutableList(),
    onItemClick: ((TaskMenuModel) -> Unit)? = null,
) {
    PopupMenuRoot.popup?.dismiss()
    PopupMenuRoot.popup = PopupMenuSetting { view, popupWindow, dimPopupWindow ->
        val rvRoot = view.findViewById<RecyclerView>(R.id.rv_popup_more_option_root)
        var popupAdapter: MenuAdapter? = null
        popupAdapter = MenuAdapter { moreOptionModel, position ->
            dimPopupWindow?.dismiss()
            popupWindow?.dismiss()
            onItemClick?.invoke(TaskMenuModel(moreOptionModel, popupAdapter, position))
        }
        rvRoot.adapter = popupAdapter
        rvRoot.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        popupAdapter.setData(dataList)
        popupAdapter.notifyDataSetChanged()
        
        rvRoot.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width: Int = rvRoot.measuredWidth
        val height: Int = rvRoot.measuredHeight
        height
    }
            .setBackground(PopupModuleResAttribute(this, R.attr.popup_module_dr_spinner).getDrawable())
            .showPopup(
                    this as Activity,
                    targetView,
                    R.layout.popup_module_root_popup_more_option
            )
}


fun Context.showPopupOnClicked(
    targetView: View,
    layout: Int,
    onViewCreate: (ViewCreateModel) -> Unit,
) {
    PopupMenuRoot.popup?.dismiss()
    PopupMenuRoot.popup = PopupMenuSetting { view, popupWindow, dimPopupWindow ->
        onViewCreate(ViewCreateModel(view, popupWindow, dimPopupWindow))
        view.rootView.height
    }
            .setBackground(PopupModuleResAttribute(this, R.attr.popup_module_dr_spinner).getDrawable())
            .showPopup(this as Activity, targetView, layout, false)
}

fun Context.showPopupMenuOnClicked(
    targetView: View,
    dataList: MutableList<MenuModel> = emptyArray<MenuModel>().toMutableList(),
    onItemClick: ((TaskMenuModel) -> Unit)? = null,
) {
    PopupMenuRoot.popup?.dismiss()
    PopupMenuRoot.popup = PopupMenuSetting { view, popupWindow, dimPopupWindow ->
        val rvRoot = view.findViewById<RecyclerView>(R.id.rv_popup_more_option_root)
        var popupAdapter: MenuAdapter? = null
        popupAdapter = MenuAdapter { moreOptionModel, position ->
            popupWindow?.dismiss()
            onItemClick?.invoke(TaskMenuModel(moreOptionModel, popupAdapter, position))
        }
        
        rvRoot.adapter = popupAdapter
        rvRoot.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        popupAdapter.setData(dataList)
        popupAdapter.notifyDataSetChanged()
        
        rvRoot.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val width: Int = rvRoot.measuredWidth
        val height: Int = rvRoot.measuredHeight
        height
    }
            .setBackground(PopupModuleResAttribute(this, R.attr.popup_module_dr_spinner).getDrawable())
            .showPopup(this as Activity, targetView, R.layout.popup_module_root_popup_more_option, false)
    
}
