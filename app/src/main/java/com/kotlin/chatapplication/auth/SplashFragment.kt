package com.kotlin.chatapplication.auth

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kotlin.chatapplication.MainActivity
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.databinding.FragmentSignInBinding
import com.kotlin.chatapplication.databinding.FragmentSignUpBinding

@Suppress("DEPRECATION")
class SplashFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.binding= FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    private fun delayed(){
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_signInFragment)

            //findNavController().navigate(R.id.action_from_fragmentA_to_fragmentB)

        }, 3000) // 3000 is the delayed time in milliseconds.
    }


    }
