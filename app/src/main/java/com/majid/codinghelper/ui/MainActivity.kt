package com.majid.codinghelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.majid.codinghelper.R
import com.majid.codinghelper.common.BottomSheet
import com.majid.codinghelper.common.IListeners
import com.majid.codinghelper.common.ToolbarSetup
import com.majid.codinghelper.databinding.ActivityMainBinding
import com.majid.codinghelper.ui.fragments.DashboardFragment
import com.majid.codinghelper.utils.ViewUtils
import com.majid.codinghelper.viewmodels.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        observeViewModel()
        mainViewModel.setOpenDashboardFragment(arrayOf<Any>(1,2))




    }




    private fun observeViewModel() {

        mainViewModel.getOpenDashboardFragment().observe(this) { event ->
            val objects: Array<Any>? = event.getContentIfNotHandled()
            if (objects != null) {
                openFragment(DashboardFragment.newInstance(), "")
            }
        }

    }
    fun openFragment(fragment: Fragment, tag: String){

        val transaction = supportFragmentManager.beginTransaction()
        setFragmentTransactionAnimation(transaction, tag)
        transaction.replace(R.id.mainContainer, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }
    private fun setFragmentTransactionAnimation(transaction: FragmentTransaction, tag: String) {
        transaction.setCustomAnimations(
            R.anim.enter_from_right,
            R.anim.exit_to_left,
            R.anim.enter_from_left,
            R.anim.exit_to_right
        )
    }



    private fun bottomSheetClicks() {
    }

    private fun initViewModel() {

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }


}