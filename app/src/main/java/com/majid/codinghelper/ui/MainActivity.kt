package com.majid.codinghelper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.majid.codinghelper.common.BottomSheet
import com.majid.codinghelper.common.IListeners
import com.majid.codinghelper.common.ToolbarSetup
import com.majid.codinghelper.databinding.ActivityMainBinding
import com.majid.codinghelper.utils.ViewUtils
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var toolbarSetup: ToolbarSetup



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbarSetup = ToolbarSetup(binding.toolbar, this)



        setListeners()

    }

    private fun setListeners() {
        toolbarSetup.binding.ivMenu.setOnClickListener {

           BottomSheet.datePickerDialog(this,object :BottomSheet.IDateSelected{
               override fun onDateSelected(date: String) {

                   ViewUtils.showToast(this@MainActivity,date)
               }

           })


        }




    }

    private fun bottomSheetClicks() {
    }


}