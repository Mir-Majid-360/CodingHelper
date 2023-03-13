package com.majid.codinghelper.lib

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class BaseClass : AppCompatActivity() {

    var isPermissionGranted = false
    protected var baseApcContext: Context? = null
    protected lateinit var baseApcContext2: Context

    protected lateinit var imageView: ImageView
    protected val REQUEST_ID_MULTIPLE_PERMISSIONS = 101

    var STORAGE_PERMISSION_CODE = 1


    fun openFileExplorer() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select Picture"
            ), STORAGE_PERMISSION_CODE
        )
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos)
        val b = baos.toByteArray()
        Base64.encodeToString(b, Base64.DEFAULT)
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
    open fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte =
                Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }

    fun requestPermission(activity: Activity){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_ID_MULTIPLE_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode.equals(REQUEST_ID_MULTIPLE_PERMISSIONS) && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isPermissionGranted = true
        }
    }


    fun isPermissionGranted(activity: Activity):Boolean{
        if (isPermissionGranted){
            return true
        }else{
            requestPermission(activity)
        }
        return isPermissionGranted
    }




}