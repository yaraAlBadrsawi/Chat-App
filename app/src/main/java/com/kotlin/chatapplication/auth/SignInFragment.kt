package com.kotlin.chatapplication.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding=FragmentSignInBinding.inflate(inflater)
        this.firebaseAuth = FirebaseAuth.getInstance()

        binding.noAccount.setOnClickListener{
            findNavController()
                .navigate(R.id.action_signInFragment_to_signUpFragment)
            Toast.makeText(activity,"no account",Toast.LENGTH_LONG).show()
        }

        binding.btnLogin.setOnClickListener{
        login()
        }

        return binding.root
    }

    private fun login() {
        val email=binding.emailEdit.text.toString()
        val pass=binding.loginPass.text.toString()

        if(email.isEmpty()||pass.isEmpty()){
            Toast.makeText(activity,"field can't be empty", Toast.LENGTH_LONG).show()

        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnSuccessListener {
                    Toast.makeText(activity,"sign in done", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                }
                .addOnFailureListener{
                    Toast.makeText(activity,"error occur,Try again", Toast.LENGTH_LONG).show()

                }



        }
        }


}