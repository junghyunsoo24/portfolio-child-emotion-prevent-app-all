package com.example.portfoliochildemotionpreventappall.expertChildStatistics

data class ExpertChildStatisticsDataResponse(
    val statistics: List<Statistics>
)

data class Statistics(
    val date: String,
    val pleasure: Int,
    val anxiety: Int,
    val sorrow: Int,
    val embarrassed: Int,
    val anger: Int,
    val hurt: Int
)