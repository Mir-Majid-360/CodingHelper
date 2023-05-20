package com.majid.codinghelper.auth.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.majid.codinghelper.R
import com.majid.codinghelper.databinding.FragmentFacebookProfileBinding

class FacebookProfileFragment() : Fragment() {

    lateinit var binding: FragmentFacebookProfileBinding


    constructor(objects: Array<Any>) : this() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFacebookProfileBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FacebookProfileFragment()

        @JvmStatic
        fun newInstance(objects: Array<Any>) =
            FacebookProfileFragment(objects)


    }
}