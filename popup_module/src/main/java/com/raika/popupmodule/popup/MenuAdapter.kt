package com.raika.popupmodule.popup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.raika.popupmodule.R
import java.util.*

class MenuAdapter(private val itemClick: (MenuModel, position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: MutableList<MenuModel> = ArrayList()

    companion object {
        const val TYPE_ITEM = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.root_adapter_more_option, parent, false)
        return MyViewHolderItem(itemView)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        val context = holder.itemView.context
        when (holder.itemViewType) {

            TYPE_ITEM -> {
                holder as MyViewHolderItem
                holder.cvRoot.setOnClickListener { itemClick(data, position) }

                if (data.icon == null) {
                    holder.ivIcon.visibility = View.INVISIBLE
                } else {
                    holder.ivIcon.visibility = View.VISIBLE
                    data.icon?.let { holder.ivIcon.setImageResource(it) }
                }
                
                if (!data.isCheck) {
                    holder.ivCheck.visibility = View.GONE
                }

                holder.tvTitle.text = data.title
            }
        }
    }

    override fun getItemCount() = dataList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    fun setData(dataList: List<MenuModel>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
    }

    fun getData(): MutableList<MenuModel> {
        return this.dataList
    }

    internal inner class MyViewHolderItem(view: View) : RecyclerView.ViewHolder(view) {
        val cvRoot = view.findViewById<MaterialCardView>(R.id.cv_adapter_more_option_root)
        val tvTitle = view.findViewById<TextView>(R.id.tv_adapter_more_option_root_title)
        val ivIcon = view.findViewById<AppCompatImageView>(R.id.iv_adapter_more_option_root_icon)
        val ivCheck = view.findViewById<AppCompatImageView>(R.id.iv_adapter_more_option_root_check)
    }


}