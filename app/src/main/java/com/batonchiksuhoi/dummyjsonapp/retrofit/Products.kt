package com.batonchiksuhoi.dummyjsonapp.retrofit

data class Products(
    val products: List<Product>,
    val limit: Int,
    val skip: Int,
    val total: Int,
)
