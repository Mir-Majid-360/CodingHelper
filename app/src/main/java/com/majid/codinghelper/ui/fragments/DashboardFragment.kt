package com.majid.codinghelper.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.majid.codinghelper.R
import com.majid.codinghelper.common.BottomSheet
import com.majid.codinghelper.common.ToolbarSetup
import com.majid.codinghelper.databinding.FragmentDashboardBinding
import com.majid.codinghelper.lib.BaseClass
import com.majid.codinghelper.utils.ViewUtils
import com.majid.codinghelper.viewmodels.MainViewModel
import java.io.File

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var toolbarSetup: ToolbarSetup



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSetup = ToolbarSetup(binding.toolbar, requireActivity())

        setListeners()
        initViewModel()
        requestPermission()

    }

    fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode.equals(REQUEST_ID_MULTIPLE_PERMISSIONS) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {
        toolbarSetup.binding.ivMenu.setOnClickListener {

            BottomSheet.datePickerDialog(requireActivity(), object : BottomSheet.IDateSelected {
                override fun onDateSelected(date: String) {

                    ViewUtils.showToast(requireActivity(), date)
                }

            })


        }

        binding.ivCamera.setOnClickListener {


                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 101)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101){
            var image = data?.getParcelableExtra<Bitmap>("data")
            binding.ivPhoto.setImageBitmap(image)
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    companion object {
        protected val REQUEST_ID_MULTIPLE_PERMISSIONS = 101

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DashboardFragment()


    }
}