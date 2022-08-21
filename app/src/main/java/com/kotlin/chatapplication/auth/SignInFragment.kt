package com.kotlin.chatapplication.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.databinding.FragmentSignInBinding
import java.util.regex.Pattern


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater)
        setActionOnNoAccount()
        binding.btnLogin.setOnClickListener {
            login()
        }

        return binding.root
    }

    private fun init(inflater: LayoutInflater) {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater)
        this.firebaseAuth = FirebaseAuth.getInstance()}

    private fun setActionOnNoAccount(){
        binding.noAccount.setOnClickListener {
            findNavController()
                .navigate(R.id.action_signInFragment_to_signUpFragment)
            Toast.makeText(activity, "no account", Toast.LENGTH_LONG).show()
        }
    }

    private fun login() {
        val email = binding.emailEdit.text.toString()
        val pass = binding.loginPass.text.toString()
       // addTextListener(email=email,pass=pass)

        if (checkField(email, pass)) {
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    Toast.makeText(activity, "sign in done", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

                }
                .addOnFailureListener {
                    Toast.makeText(activity, "error occur,Try again", Toast.LENGTH_LONG).show()

                }

        }
    }


    fun checkField(email:String,pass:String):Boolean{
        if(email.isEmpty()){
            binding.emailEdit.error="Email Required!"
            binding.emailEdit.requestFocus()
            return false
        }

        if(pass.isEmpty()) {
            binding.emailEdit.error="password Required!"
            binding.emailEdit.requestFocus()
            return false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.emailEdit.error="please enter valid email"
            binding.emailEdit.requestFocus()
            return false
        }

        if(pass.length<6){
            binding.inputPass.error="password should be 6 at least"
            binding.inputPass.requestFocus()
            return false
        }
        return true
    }

    private fun addTextListener(email: String,pass:String) {
        binding.emailEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                    binding.btnLogin.isEnabled=email.trim().isNotEmpty()
                            &&pass.trim().isNotEmpty()

            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })
    }

    fun check(){
        if(this.firebaseAuth.currentUser?.uid!=null){
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }
    }

}