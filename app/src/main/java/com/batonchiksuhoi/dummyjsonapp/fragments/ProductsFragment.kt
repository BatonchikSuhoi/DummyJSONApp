package com.batonchiksuhoi.dummyjsonapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.batonchiksuhoi.dummyjsonapp.LoginViewModel
import com.batonchiksuhoi.dummyjsonapp.ProductViewModel
import com.batonchiksuhoi.dummyjsonapp.R
import com.batonchiksuhoi.dummyjsonapp.adapter.ProductAdapter
import com.batonchiksuhoi.dummyjsonapp.databinding.FragmentProductsBinding
import com.batonchiksuhoi.dummyjsonapp.retrofit.MainApi
import com.batonchiksuhoi.dummyjsonapp.retrofit.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ProductsFragment : Fragment(), ProductAdapter.Listener{

    private lateinit var binding: FragmentProductsBinding
    private lateinit var mainApi: MainApi
    private lateinit var adapter: ProductAdapter
    //private lateinit var token: String

    private val loginViewModel: LoginViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        initRcView()

        loginViewModel.token.observe(viewLifecycleOwner){
                token ->
            CoroutineScope(Dispatchers.IO).launch {
                val productsList = mainApi.getAllProducts(token)
                requireActivity().runOnUiThread{
                    adapter.submitList(productsList.products)
                }
            }
        }

        binding.productsSearchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {

                loginViewModel.token.observe(viewLifecycleOwner){
                    token ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val productsList = mainApi.getAllProductsByTag(token,text)
                        requireActivity().runOnUiThread{
                            adapter.submitList(productsList.products)
                        }
                    }
                }
                return true
            }

        })

    }

    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun initRcView() = with(binding){
        adapter = ProductAdapter(this@ProductsFragment)
        productsRcView.layoutManager = LinearLayoutManager(context)
        productsRcView.adapter = adapter
    }

    override fun onClick(product: Product) {
        //Toast.makeText(requireActivity(), "${product.title} tapped", Toast.LENGTH_LONG).show()
        productViewModel.product.value = product
        
        findNavController().navigate(R.id.action_ProductsFragment_to_ProductInfoFragment)

    }

}