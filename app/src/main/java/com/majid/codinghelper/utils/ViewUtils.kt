package com.majid.codinghelper.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.majid.codinghelper.R
import com.majid.codinghelper.animations.AnimationUtils
import okhttp3.*
import org.jsoup.Jsoup
import java.io.IOException

class ViewUtils {

    companion object{
        lateinit var  pd:ProgressDialog

        fun showToast(context: Context ,msg:String)
        {

            val customToastLayout =(context as Activity).layoutInflater.inflate(R.layout.toast,(context as Activity).findViewById(R.id.toast_container))
            val customToast = Toast(context)
            val text:TextView=customToastLayout.findViewById(R.id.text)
            text.text=msg
            customToast.view = customToastLayout
            customToast.setGravity(Gravity.CENTER,0,0)
            customToast.duration = Toast.LENGTH_SHORT
            customToast.show()

        }

        fun showToast(context: Context ,msg:String,isError:Boolean)
        {

            val customToastLayout =(context as Activity).layoutInflater.inflate(R.layout.toast,(context as Activity).findViewById(R.id.toast_container))
            val customToast = Toast(context)
            val text:TextView=customToastLayout.findViewById(R.id.text)
            val icon:ImageView=customToastLayout.findViewById(R.id.icon)
            if(isError)
            {
//                icon.setImageDrawable(context.getDrawable(R.drawable.icon_error))
            }else{
//                icon.setImageDrawable(context.getDrawable(R.drawable.icon_ok))
            }
            text.text=msg
            customToast.view = customToastLayout
            customToast.setGravity(Gravity.CENTER,0,0)
            customToast.duration = Toast.LENGTH_SHORT
            customToast.show()

        }

        fun showProgressDialog(context: Context,msg:String)
        {
            pd   = ProgressDialog(context)
            pd.setTitle(msg)
            pd.setMessage("Progressing..")
            pd.show()
        }

        fun hideProgressDialog()
        {
            pd?.hide()!!

        }









        fun showSoftKeyBoard(activity: Activity?, rootView: View) {
            if (activity != null) {
                rootView.requestFocus()
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(rootView, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun showKeyBoard(context: Context,rootView: View) {
            if (context != null) {
                rootView.requestFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(rootView, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun getTextFromHTML(string: String):String{

            return  Jsoup.parse(string).text()
        }

//        fun fetchSvg(context: Context, url: String, target: ImageView) {
//
//
//
//
//            val httpClient = OkHttpClient.Builder()
//                .cache(Cache(context.cacheDir, 5 * 1024 * 1014))
//                .build()
//
//            val request: Request = Request.Builder().url(url).build()
//            httpClient.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    // we are adding a default image if we gets any error.
//                    //target.setImageResource(R.drawable.ic_app_logo)
//                }
//
//
//                @Throws(IOException::class)
//                override fun onResponse(call: Call, response: Response) {
//
//                    response.body?.apply {
//                        val stream = this.byteStream()
//
//                        Sharp.loadInputStream(stream).into(target)
//                        stream.close()
//                    }
//                }
//            })
//        }

//        fun addBottomDots(context:Context, layout: LinearLayout, size:Int, currentPage: Int) {
//            val dots = arrayOfNulls<ImageView>(size)
//            layout.removeAllViews()
//            for (i in dots.indices) {
//                dots[i] = ImageView(context)
//                dots[i]?.setPadding(3,0,3,0)
//                if(i!=currentPage) {
//                    dots[i]!!.setImageDrawable(context.getDrawable(R.drawable.icon_dot_normal))
//                }else{
//                    dots[i]!!.setImageDrawable(context.getDrawable(R.drawable.icon_dot_active))
//                    dots[i]!!.animation= AnimationUtils.getFadeInAnimation(context)
//                }
//                layout.addView(dots[i])
//            }
//        }

        fun smoothScrollTop(parentScrollView: NestedScrollView) {
            parentScrollView.fullScroll(ScrollView.FOCUS_UP);
            parentScrollView.setSmoothScrollingEnabled(true)
        }


//        fun swipeAsBlock(recyclerView: RecyclerView, blockSize:Int): SnapToBlock {
//            val snapToBlock =
//                SnapToBlock(
//                    blockSize
//                )
//            try {
//
//                snapToBlock.attachToRecyclerView(recyclerView)
//            }catch (e:Exception){}
//
//            return  snapToBlock;
//
//        }



    }
}