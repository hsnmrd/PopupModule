package com.raika.popupmodule.popup

data class MenuModel(
        var icon: Int? = null,
        var id: Long = -1,
        var title: String = "",
        var type: String = "",
        var isCheck: Boolean = false,
        var typeItem: Int = 1
)