package com.example.portfoliochildemotionpreventappall.managerChildList

data class ManagerChildListDataResponse (
    val child: List<Child>
)

data class Child(
    val id: String,
    val name: String,
    val phone_num: String,
    val address: String
)