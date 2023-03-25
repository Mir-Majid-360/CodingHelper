package com.majid.codinghelper.auth.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.majid.codinghelper.R
import com.majid.codinghelper.auth.authviewmodel.AuthViewModel
import com.majid.codinghelper.auth.ui.fragments.LoginFragment
import com.majid.codinghelper.auth.ui.fragments.SignUpFragment
import com.majid.codinghelper.databinding.ActivityAuthBinding
import com.majid.codinghelper.utils.ViewUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        observeViewModel()
        authViewModel.setOpenLoginFragment(arrayOf(1, 2))

//        getCountriesData()
    }

//    private fun getCountriesData() {
//
//        val tm = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//        var regionCode = ""
//        regionCode = tm.networkCountryIso
//        ViewUtils.showToast(this,regionCode)
//    }

    private fun observeViewModel() {

        authViewModel.getOpenSignUpFragment().observe(this) { event ->
            val objects: Array<Any>? = event.getContentIfNotHandled()
            if (objects != null) {
                openFragment(SignUpFragment.newInstance(objects), "")
            }
        }
        authViewModel.getOpenLoginFragment().observe(this) { event ->
            val objects: Array<Any>? = event.getContentIfNotHandled()
            if (objects != null) {
                openFragment(LoginFragment.newInstance(objects), "")
            }
        }

    }

    private fun openFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        setFragmentTransactionAnimation(transaction, tag)
        transaction.replace(R.id.authContainer, fragment, tag)
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


    private fun initViewModel() {

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
    }


}