package com.example.portfoliochildemotionpreventappall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfoliochildemotionpreventappall.R
import com.example.portfoliochildemotionpreventappall.expertChildList.Child

class ExpertChildListAdapter(var expertChildList: List<Child>, private val onItemClick: (Child) -> Unit) :
    RecyclerView.Adapter<ExpertChildListAdapter.ExpertChildViewHolder>() {

    class ExpertChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertChildInfoTextView: TextView = itemView.findViewById(R.id.childInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertChildViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expert_childlist, parent, false)
        return ExpertChildViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpertChildViewHolder, position: Int) {
        val child = expertChildList[position]
        val childInfo = "이름: ${child.name}\n 아이디: ${child.id}\n 비밀번호: ${child.pw}\n" +
                "주소: ${child.address}\n 핸드폰번호: ${child.phone_num}\n 위험 상태: ${child.at_risk_child_status}\n"+
                "감정: ${child.sentiment}\n"

        holder.expertChildInfoTextView.text = childInfo

        holder.itemView.setOnClickListener {
            onItemClick(child)
        }
    }

    override fun getItemCount() = expertChildList.size
}