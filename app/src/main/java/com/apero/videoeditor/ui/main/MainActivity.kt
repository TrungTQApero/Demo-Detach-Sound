package com.apero.videoeditor.ui.main

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.apero.videoeditor.core.BaseActivity
import com.apero.videoeditor.R
import com.apero.videoeditor.databinding.ActivityMainBinding


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    var navHostFragment : NavHostFragment? = null
    val MAX_BUNDLE_SIZE = 300

    override fun getLayoutRes(): Int {
        return  R.layout.activity_main
    }

    override fun initViewModel(viewModel: MainViewModel) {

    }

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.container_fragment) as NavHostFragment
        navController = navHostFragment!!.navController

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        //DataUtils.inCreaseCount()
    }

    override fun getStatusBar(): Pair<Int, Boolean> {
        return Pair(first = R.color.globalBlack, second = false)
    }

    override fun initView() {
    }
}