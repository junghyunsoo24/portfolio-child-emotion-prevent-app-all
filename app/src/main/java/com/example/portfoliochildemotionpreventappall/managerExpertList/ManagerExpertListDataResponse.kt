package com.example.portfoliochildemotionpreventappall.managerExpertList

data class ManagerExpertListDataResponse(
    val expert: List<Expert>
)

data class Expert(
    val id: String,
    val name: String,
    val institution: String
)