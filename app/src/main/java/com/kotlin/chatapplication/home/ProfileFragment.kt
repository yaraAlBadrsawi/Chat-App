package com.kotlin.chatapplication.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kotlin.chatapplication.databinding.FragmentProfileBinding
import java.util.*


class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private val TAG = "ProfileFragment"
    private var profileImageUri: Uri? = null


    private val currentUserRef: StorageReference
        get() = FirebaseStorage.getInstance().reference
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        init(inflater)
        pickImage()
        showProfileImage()



        return binding.root
    }


    private fun init(inflater: LayoutInflater) {
        this.binding = FragmentProfileBinding.inflate(inflater)

    }

    private fun pickImage() {

//        val img=registerForActivityResult( ActivityResultContracts.GetContent()) { uri: Uri? ->
//            uri?.let {
//                profileImageUri = uri
//                Log.d(TAG, "pickImage: profileImageURI:::$uri")
//                binding.profileImage.setImageURI(it)
//            }
//        }

        val image = registerForActivityResult(ActivityResultContracts.GetContent()) {

            binding.profileImage.setImageURI(it)
            uploadImageToStorage(it) {
                Log.d(TAG, "pickImage: $it")
                val map = mutableMapOf<String, Any>()
                map["profileImage"] = it
                val firstore: FirebaseFirestore = FirebaseFirestore.getInstance()
                firstore.collection("image").document("img")
                    .set(map)
            }
        }

        binding.addImage.setOnClickListener {
            image.launch("image/*")
        }
    }


    private fun uploadImageToStorage(image: Uri?, onSuccess: (image: String) -> Unit) {
        val imageReference = currentUserRef.child("profileImage/${UUID.randomUUID()}")

        image?.let {
            imageReference.putFile(it).addOnSuccessListener {
                Toast.makeText(activity, "Image save to storage", Toast.LENGTH_LONG).show()

                imageReference.downloadUrl.addOnCompleteListener {
                    if (it.isSuccessful) {
                        profileImageUri = Uri.parse(it.result.toString())
                        onSuccess(profileImageUri.toString())
                        //  Log.d(TAG, "uploadImageToStorage: image saved in Firestore ")
                        Log.d(TAG, "uploadImageToStorage:  ${profileImageUri.toString()} ")
                    } else {
                        Log.d(TAG, "uploadImageToStorage: image not saved in Firestore ")
                    }


                }
            }
                .addOnFailureListener {
                    Toast.makeText(
                        activity,
                        "Some error occur while uploading image ",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "uploadImageToStorage: Error ::: $it")
                }
        }



    }

 private fun showProfileImage(){
//     Glide.with(requireActivity().baseContext)
//         .load(currentUserRef.storage.reference)
//         .into(binding.profileImage)
 }

}
