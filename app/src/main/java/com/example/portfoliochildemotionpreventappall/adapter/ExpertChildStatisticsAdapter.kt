package com.example.portfoliochildemotionpreventappall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.portfoliochildemotionpreventappall.R
import com.example.portfoliochildemotionpreventappall.expertChildStatistics.Statistics

class ExpertChildStatisticsAdapter(var childStatistics: List<Statistics>, private val onItemClick: (Statistics) -> Unit) :
    RecyclerView.Adapter<ExpertChildStatisticsAdapter.ExpertChildStatisticsViewHolder>() {

    class ExpertChildStatisticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expertChildStatisticsInfoTextView: TextView = itemView.findViewById(R.id.childInfoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertChildStatisticsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expert_childlist, parent, false)
        return ExpertChildStatisticsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExpertChildStatisticsViewHolder, position: Int) {
        val statistics = childStatistics[position]
        val statisticsInfo = " 날짜: ${statistics.date}\n 기쁨: ${statistics.pleasure}\n 불안: ${statistics.anxiety}\n" +
                "슬픔: ${statistics.sorrow}\n 당황: ${statistics.embarrassed}\n 화남: ${statistics.anger}\n"+
                "상처: ${statistics.hurt}\n"

        holder.expertChildStatisticsInfoTextView.text = statisticsInfo

        holder.itemView.setOnClickListener {
            onItemClick(statistics)
        }
    }

    override fun getItemCount() = childStatistics.size
}