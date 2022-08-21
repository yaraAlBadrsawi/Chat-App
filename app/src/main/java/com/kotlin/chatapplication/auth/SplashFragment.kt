package com.kotlin.chatapplication.auth

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        this.binding= FragmentSplashBinding.inflate(inflater)
        delayed()
        return binding.root
    }

    private fun delayed(){
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)

        }, 3000) // 3000 is the delayed time in milliseconds.
    }


    }
