package com.kotlin.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.chatapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}