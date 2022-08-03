package com.kotlin.chatapplication.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kotlin.chatapplication.R
import com.kotlin.chatapplication.constants.Constants
import com.kotlin.chatapplication.databinding.FragmentSignUpBinding

import com.kotlin.chatapplication.model.User

class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        initObjects(inflater)
        this.binding.btnLogin.setOnClickListener {
            register()
        }

        return binding.root
    }

    private fun initObjects(inflater: LayoutInflater) {
        this.binding = FragmentSignUpBinding.inflate(inflater)
        this.firebaseAuth = FirebaseAuth.getInstance()
        this.firestore = FirebaseFirestore.getInstance()
    }

    private fun register() {

        val email = binding.regisEmail.text.toString()
        val password = binding.regisPassword.text.toString()
        val name= binding.regisUser.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(activity,"field can't be empty",Toast.LENGTH_LONG).show()
        }

        else{

            firebaseAuth.createUserWithEmailAndPassword(
                email, password
            ).addOnSuccessListener {
                // go to home
                Toast.makeText(activity,"create account done", Toast.LENGTH_LONG).show()

                this.addUserToFirestore(User(
                    email = email,
                    name = name,
                    uid = firebaseAuth.currentUser?.uid!!,
                ))
            }

                .addOnFailureListener {
                    Toast.makeText(activity,"some error occur", Toast.LENGTH_LONG).show()

                }
        }
    }

    private fun addUserToFirestore(user:User) {

        firestore.collection(Constants.USER_COLLECTION)
            .document(user.uid!!)
            .set(user)
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)

                Toast.makeText(activity,"user data saved",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(activity,"user data not saved",Toast.LENGTH_LONG).show()
                Log.d(TAG, "addUserToFirestore Error : "+it.message)
            }

    }


}