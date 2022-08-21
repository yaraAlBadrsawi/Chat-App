package com.kotlin.chatapplication.home

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kotlin.chatapplication.adapter.MessageAdapter
import com.kotlin.chatapplication.constants.Constants
import com.kotlin.chatapplication.databinding.FragmentRoomChatBinding
import com.kotlin.chatapplication.model.Message
import com.kotlin.chatapplication.model.User
import io.grpc.Context
import java.io.IOException
import java.util.*

class RoomChatFragment : Fragment() {

    private lateinit var binding: FragmentRoomChatBinding
    private lateinit var receiverId: String
    private lateinit var messageId: String
    private lateinit var senderId: String
    private lateinit var senderRoomId: String
    private lateinit var receiverRoomId: String
    private lateinit var messageList: MutableList<Message>
    private lateinit var messageListRec: MutableList<Message>
    private val TAG = "RoomChatFragment"
    private lateinit var messageAdapter: MessageAdapter

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUserRef: StorageReference
        get() = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiverId = arguments?.getString(Constants.USER_UID)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initObjects(inflater)
        setReceiverName()
        getMessageFromDB()
        binding.btnSendText.setOnClickListener {
            createChatRoom()
            messageList.clear()
        }
        return binding.root
    }
    private fun setReceiverName() {
        firestore.collection(Constants.USER_COLLECTION).document(receiverId)
            .get().addOnSuccessListener {
                val user = it.toObject(User::class.java)

                binding.toolbar.title = user?.name

            }
    }

    private fun initObjects(inflater: LayoutInflater) {
        this.binding = FragmentRoomChatBinding.inflate(inflater)
        this.senderId = FirebaseAuth.getInstance().currentUser?.uid!!
        this.senderRoomId = senderId + receiverId
        this.receiverRoomId = receiverId + senderId
        this.messageList = mutableListOf()
        this.messageAdapter = MessageAdapter(context as Activity, messageList)
    }

    private fun getMessageFromDB() {
        // get data in sender side
      //  messageList.clear()
        val senderQuery = firestore.collection(Constants.USER_COLLECTION)
            .document(senderId)
            .collection(receiverRoomId)
            .orderBy(Constants.CURRENT_TIME, Query.Direction.DESCENDING)

        senderQuery.addSnapshotListener { querySnapshot, _ ->
            querySnapshot?.documents?.forEach {
                messageList.add(it.toObject(Message::class.java)!!)
            }
            addMessagesToRecycler()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMessagesToRecycler() {
        binding.rvChat.adapter = messageAdapter
        messageAdapter.notifyDataSetChanged()
    }


    @SuppressLint("SimpleDateFormat")
    private fun createChatRoom() {
        messageId = UUID.randomUUID().toString()
        if (binding.etMessage.text.isNotEmpty()) {


            val message = Message(
                message = binding.etMessage.text.toString(),
                senderId = senderId,
                receiverId = receiverId,
                messageId = messageId,
                currentTime = Calendar.getInstance().time
            )

            // receiver side :
            firestore.collection(Constants.USER_COLLECTION)
                .document(receiverId)
                .collection(senderRoomId)// room Id
                .document(messageId) // or we can use current time
                .set(message)


            // sender side :
            firestore.collection(Constants.USER_COLLECTION)
                .document(senderId)
                .collection(receiverRoomId) // room Id
                .document(messageId)
                .set(message)

            binding.etMessage.text.clear()
        } else {
            binding.etMessage.error = "type something!"
        }

    }



    private fun pickImage() {

        val image = registerForActivityResult(ActivityResultContracts.GetContent()) {

         //   binding.profileImage.setImageURI(it)
            val selectImagePath=it
           // val selectImageBitmap=MediaStore.Images.Media.getBitmap()
            uploadImageToStorage(it) {
                Log.d(TAG, "pickImage: $it")

            }
        }

        binding.btnSendImage.setOnClickListener {
            image.launch("image/*")
        }
    }



    private fun uploadImageToStorage(image: Uri?, onSuccess: (image: String) -> Unit) {
        val imageReference = currentUserRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())


       // val imageReference = currentUserRef
          //  .child("images/${UUID.randomUUID()}").putBytes(readBytes(requireActivity(), image!!)!!)

        image?.let {
         //   imageReference.putFile(it).addOnSuccessListener {
              //  Toast.makeText(activity, "Image save to storage", Toast.LENGTH_LONG).show()

               // imageReference.downloadUrl.addOnCompleteListener {


                }
            }
//                .addOnFailureListener {
//                    Toast.makeText(
//                        activity,
//                        "Some error occur while uploading image ",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    Log.d(TAG, "uploadImageToStorage: Error ::: $it")
//                }
//        }
}
//    @Throws(IOException::class)
  //  private fun readBytes(activity: Activity, uri: Uri): ByteArray? =
        //context.contentResolver.openInputStream(uri)?.buffered()?.use { it.readBytes() }


