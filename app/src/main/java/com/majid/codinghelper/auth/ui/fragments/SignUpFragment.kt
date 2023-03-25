package com.majid.codinghelper.auth.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.majid.codinghelper.auth.authviewmodel.AuthViewModel
import com.majid.codinghelper.databinding.FragmentSignUpBinding

class SignUpFragment() : Fragment() {



    lateinit var binding: FragmentSignUpBinding
    lateinit var authViewModel: AuthViewModel

    constructor(objects: Array<Any>):this(){

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setListeners()

    }

    private fun setListeners() {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SignUpFragment()

        @JvmStatic
        fun newInstance(objects :Array<Any>) =
            SignUpFragment(objects)


    }

    private fun initViewModel() {


        authViewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
    }
}