package com.example.portfoliochildemotionpreventappall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfoliochildemotionpreventappall.R
import com.example.portfoliochildemotionpreventappall.managerChildList.Child

class ManagerChildListAdapter(var managerChildList: List<Child>, private val onItemClick: (Child) -> Unit) :
    RecyclerView.Adapter<ManagerChildListAdapter.ManagerChildViewHolder>() {

    class ManagerChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val managerChildInfoTextView: TextView = itemView.findViewById(R.id.childInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManagerChildViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manager_childlist, parent, false)
        return ManagerChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ManagerChildViewHolder, position: Int) {
        val child = managerChildList[position]
        val childInfo = "아이디: ${child.id}\n 이름: ${child.name}\n" +
                "핸드폰번호: ${child.phone_num}\n"+
                "주소: ${child.address}\n"
        holder.managerChildInfoTextView.text = childInfo

        holder.itemView.setOnClickListener {
            onItemClick(child)
        }
    }

    override fun getItemCount() = managerChildList.size
}