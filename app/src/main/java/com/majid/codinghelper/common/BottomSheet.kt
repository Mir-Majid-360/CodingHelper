package com.majid.codinghelper.common

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.majid.codinghelper.R
import java.util.*


class BottomSheet() {

    interface IDateSelected {
        fun onDateSelected(date: String)
    }

    companion object {


        @RequiresApi(Build.VERSION_CODES.O)
        fun datePickerDialog(activity: Activity, callBack: IDateSelected) {

            var date = ""
            val dialog = BottomSheetDialog(activity)
            val view = activity.layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
            val close = view.findViewById<View>(R.id.helperClose)
            val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
            datePicker.visibility = View.VISIBLE


            val btnCancel = view.findViewById<TextView>(R.id.tvBtnCancel)
            val btnOK = view.findViewById<TextView>(R.id.tvBtnOk)
            close.setOnClickListener {
                dialog.dismiss()
            }
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }






            btnOK.setOnClickListener {

                val day = datePicker.dayOfMonth
                val month = datePicker.month + 1
                val year = datePicker.year

                callBack.onDateSelected("${day},${month},${year}")

                dialog.dismiss()


            }





            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()

        }
    }


}