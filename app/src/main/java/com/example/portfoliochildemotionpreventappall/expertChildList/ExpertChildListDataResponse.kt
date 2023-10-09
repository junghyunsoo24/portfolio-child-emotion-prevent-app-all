package com.example.portfoliochildemotionpreventappall.expertChildList

data class ExpertChildListDataResponse(
    val result: List<Child>
)

data class Child(
    val id: String,
    val pw: String,
    val name: String,
    val age: Int,
    val address: String,
    val phone_num: String,
    val at_risk_child_status: Int,
    val sentiment: Int
)