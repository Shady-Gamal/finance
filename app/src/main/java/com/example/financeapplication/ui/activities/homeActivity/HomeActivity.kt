package com.example.financeapplication.ui.activities.homeActivity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.domain.entities.DataUtils
import com.example.financeapplication.R
import com.example.financeapplication.databinding.ActivityHomeBinding
import com.example.financeapplication.databinding.DrawerHeaderBinding
import com.example.financeapplication.ui.activities.authenticationActivity.AuthenticationActivity
import com.example.financeapplication.ui.fragments.loading.LoadingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

   lateinit var binding : ActivityHomeBinding
   private lateinit var headerBinding: DrawerHeaderBinding
   val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {

            setKeepOnScreenCondition{
                return@setKeepOnScreenCondition !viewModel.isReady.value
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.drawer_header, binding
                .navView, false
        )
        binding.navView.addHeaderView(headerBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.home_nav_graph)
        binding.navView.setupWithNavController(navController)
        navController.graph = navGraph
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.userDataState.collect{uiState ->

                    if (uiState.isLoaded != null){


                        navController.popBackStack()
                        navController.navigate(R.id.homeFragment)
                        headerBinding.invalidateAll()


                    }else if (uiState.doesntexist){

                        val intent = Intent(this@HomeActivity,AuthenticationActivity::class.java)
                        this@HomeActivity.startActivity(intent)
                        this@HomeActivity.finish()

                    }else if (uiState.error != null){

                        Toast.makeText( this@HomeActivity, "error loading data" , Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }











        headerBinding.dataUtils = DataUtils


        headerBinding.closeText.setOnClickListener {

            binding.drawerLayout.close()
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    viewModel.refreshDrawerHeader.collect() {
                        headerBinding.invalidateAll()

                    }
                }
            }
        }



        binding.logoutText.setOnClickListener {

                    viewModel.signOut()
        }

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {

                        viewModel.logoutState.collect { uiState ->
                            if (uiState.isLoggedOut) {

                                DataUtils.user = null
                                val intent = Intent(this@HomeActivity,AuthenticationActivity::class.java)
                                this@HomeActivity.startActivity(intent)
                                this@HomeActivity.finish()

                            } else if (uiState.isLoading) {
                                LoadingFragment().show(
                                    supportFragmentManager, "homeboi"
                                )
                            } else if (!(uiState.error.isNullOrBlank())) {
                                Toast.makeText(
                                    this@HomeActivity,
                                    uiState.error,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }
                    }
                }




            }
    }


