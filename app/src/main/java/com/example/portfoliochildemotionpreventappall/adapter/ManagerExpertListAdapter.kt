package com.example.portfoliochildemotionpreventappall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfoliochildemotionpreventappall.R
import com.example.portfoliochildemotionpreventappall.managerExpertList.Expert


class ManagerExpertListAdapter(var expertList: List<Expert>, private val onItemClick: (Expert) -> Unit) :
    RecyclerView.Adapter<ManagerExpertListAdapter.ExpertViewHolder>() {

    class ExpertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertInfoTextView: TextView = itemView.findViewById(R.id.expertInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manager_expertlist, parent, false)
        return ExpertViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpertViewHolder, position: Int) {
        val expert = expertList[position]
        val expertInfo = "아이디: ${expert.id}\n 이름: ${expert.name}\n 기관: ${expert.institution}\n"
        holder.expertInfoTextView.text = expertInfo

        holder.itemView.setOnClickListener {
            onItemClick(expert)
        }
    }

    override fun getItemCount() = expertList.size
}