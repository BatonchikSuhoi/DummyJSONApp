package com.batonchiksuhoi.dummyjsonapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.batonchiksuhoi.dummyjsonapp.LoginViewModel
import com.batonchiksuhoi.dummyjsonapp.ProductViewModel
import com.batonchiksuhoi.dummyjsonapp.R
import com.batonchiksuhoi.dummyjsonapp.adapter.ProductAdapter
import com.batonchiksuhoi.dummyjsonapp.databinding.FragmentProductInfoBinding
import com.batonchiksuhoi.dummyjsonapp.databinding.FragmentProductsBinding
import com.batonchiksuhoi.dummyjsonapp.retrofit.MainApi
import com.squareup.picasso.Picasso


class ProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentProductInfoBinding
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(productViewModel.product.value?.images?.get(1)).into(binding.productImage)
        binding.productTitle.text = productViewModel.product.value?.title
        binding.productDescription.text = productViewModel.product.value?.description
        binding.productPrice.text = productViewModel.product.value?.price.toString()
        binding.productDiscount.text = productViewModel.product.value?.discountPercentage.toString()
        binding.productRating.text = productViewModel.product.value?.rating.toString()
        binding.productStock.text = productViewModel.product.value?.stock.toString()
        binding.productBrand.text = productViewModel.product.value?.brand.toString()
        binding.productCategory.text = productViewModel.product.value?.category.toString()
    }

}