package com.batonchiksuhoi.dummyjsonapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.batonchiksuhoi.dummyjsonapp.databinding.ContentBasicBinding

class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ContentBasicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ContentBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}