package com.kotlin.chatapplication.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import java.io.InputStream

class Glide:AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
       registry.append(StorageReference::class.java
           ,InputStream::class.java
           , FirebaseImageLoader.Factory())

    }
}

