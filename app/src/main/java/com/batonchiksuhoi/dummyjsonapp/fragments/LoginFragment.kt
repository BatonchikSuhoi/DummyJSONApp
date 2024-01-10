package com.batonchiksuhoi.dummyjsonapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.batonchiksuhoi.dummyjsonapp.LoginViewModel
import com.batonchiksuhoi.dummyjsonapp.R
import com.batonchiksuhoi.dummyjsonapp.databinding.FragmentLoginBinding
import com.batonchiksuhoi.dummyjsonapp.retrofit.AuthRequest
import com.batonchiksuhoi.dummyjsonapp.retrofit.MainApi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var mainApi: MainApi
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRetrofit()
        binding.apply {
            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_LoginFragment_to_ProductsFragment)
            }

            signInButton.setOnClickListener{
                auth(
                    AuthRequest(
                        usernameField.text.toString(),
                        passwordField.text.toString(),
                    )
                )
            }
        }

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

    private fun auth(authRequest: AuthRequest){
        CoroutineScope(Dispatchers.IO).launch{
            val response = mainApi.auth(authRequest)
            val errorMessage = response.errorBody()?.string()?.let{JSONObject(it).getString("message")}
            requireActivity().runOnUiThread(){
                val user = response.body()
                if(user != null){
                    Picasso.get().load(user.image).into(binding.imageView)
                    binding.firstNameText.text = user.firstName
                    binding.lastNameText.text = user.lastName
                    binding.nextButton.visibility = View.VISIBLE
                    viewModel.token.value = user.token
                }
                else Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_LONG).show()
            }
        }


    }

}