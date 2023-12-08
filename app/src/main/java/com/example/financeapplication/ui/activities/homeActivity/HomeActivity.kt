package com.example.financeapplication.ui.activities.homeActivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entities.DataUtils
import com.example.financeapplication.R
import com.example.financeapplication.databinding.ActivityHomeBinding
import com.example.financeapplication.databinding.DrawerHeaderBinding
import com.example.financeapplication.ui.fragments.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

   lateinit var binding : ActivityHomeBinding
   private lateinit var headerBinding: DrawerHeaderBinding
   val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "created" )
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.drawer_header, binding
            .navView, false)
        binding.navView.addHeaderView(headerBinding.root)
        headerBinding.dataUtils = DataUtils

        headerBinding.closeText.setOnClickListener {

            binding.drawerLayout.close()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.topAppBar.setupWithNavController(navController,appBarConfiguration)

        binding.logoutText.setOnClickListener {

            viewModel.signOut()
        }



            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED)  {

                    viewModel.uiState.collect { uiState ->
                        if (uiState.isLoggedOut) {
                            Log.e("TAG", isTaskRoot.toString() )

                            this@HomeActivity.finish()


                            Log.e("clicked", "clicked" )
                        } else if (uiState.isLoading){
                            LoadingFragment().show(
                                supportFragmentManager, "homeboi"
                            )
                        }
                        else if (!(uiState.error.isNullOrBlank())){
                            Toast.makeText(applicationContext, uiState.error, Toast.LENGTH_SHORT).show()

                        }

                    }
                }
            }









    }










}