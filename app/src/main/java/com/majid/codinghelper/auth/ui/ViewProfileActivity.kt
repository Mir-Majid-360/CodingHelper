package com.majid.codinghelper.auth.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.majid.codinghelper.R
import com.majid.codinghelper.databinding.ActivityViewProfileBinding

class ViewProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val  accessToken = AccessToken.getCurrentAccessToken()


    }


}