package com.batonchiksuhoi.dummyjsonapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batonchiksuhoi.dummyjsonapp.retrofit.Product

class ProductViewModel: ViewModel() {
    val product = MutableLiveData<Product>()
}