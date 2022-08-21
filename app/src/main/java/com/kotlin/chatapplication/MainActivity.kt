package com.kotlin.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.AdapterView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.kotlin.chatapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val navController = findNavController(R.id.bottomNavigationView)
//        ىشر.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when(destination.id) {
//                R.id.navigation_home,R.id.navigation_profile,R.id.navigation_account -> {
//                    nav_view.visibility = View.VISIBLE
//                }
//                else -> {
//                    nav_view.visibility = View.GONE
//                }
//            }


        }

     //   binding.bottomNavigationView.
   //     NavigationUI.setupWithNavController(binding.bottomNavigationView,findNavController())


    }

