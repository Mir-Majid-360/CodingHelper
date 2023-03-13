package com.majid.codinghelper.animations
import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import okhttp3.*


class AnimationUtils {
    companion object{


//        fun autoAnimateBanner(adapter: AdapterBanner, recyclerView: RecyclerView)
//        {
//
//            // HANDLE AUTO ANIMATE
//            val speedScroll = 4000 //SLIDE AFTER 3s
//            val handler = Handler()
//            val runnable: Runnable = object : Runnable {
//                var count = 0
//                var flag = true
//                override fun run() {
//                    if(!ViewholderBannerSlot.AUTO_PLAY)
//                    {
//                        return
//                    }
//                    if (count < adapter.itemCount) {
//                        if (count == adapter.itemCount - 1) {
//                            flag = false
//                        } else if (count == 0) {
//                            flag = true
//                        }
//                        if (flag) count++ else count = 0
//                        recyclerView.smoothScrollToPosition(count)
//                        handler.postDelayed(this, speedScroll.toLong())
//                    }
//                }
//            }
//            handler.postDelayed(runnable, speedScroll.toLong())
//
//
//        }

//        fun autoAnimate(adapter: AdapterImageCarousel, recyclerView: RecyclerView)
//        {
//            // HANDLE AUTO ANIMATE
//            val speedScroll = 4000 //SLIDE AFTER 3s
//            val handler = Handler()
//            val runnable: Runnable = object : Runnable {
//                var count = 0
//                var flag = true
//                override fun run() {
//                    if(!ViewHolderImageCarousel.AUTO_PLAY)
//                    {
//                        return
//                    }
//
//
//                    if (count < adapter.itemCount) {
//                        if (count == adapter.itemCount - 1) {
//                            flag = false
//                        } else if (count == 0) {
//                            flag = true
//                        }
//                        if (flag) count++ else count = 0
//                        recyclerView.smoothScrollToPosition(count)
//                        handler.postDelayed(this, speedScroll.toLong())
//                    }
//                }
//            }
//            handler.postDelayed(runnable, speedScroll.toLong())
//
//        }

//        fun autoAnimate(adapter: AdapterBlogTeams, recyclerView: RecyclerView)
//        {
//            // HANDLE AUTO ANIMATE
//            val speedScroll = 4000 //SLIDE AFTER 3s
//            val handler = Handler()
//            val runnable: Runnable = object : Runnable {
//                var count = 0
//                var flag = true
//                override fun run() {
//
//
//                    if (count < adapter.itemCount) {
//                        if (count == adapter.itemCount - 1) {
//                            flag = false
//                        } else if (count == 0) {
//                            flag = true
//                        }
//                        if (flag) count++ else count = 0
//                        recyclerView.smoothScrollToPosition(count)
//                        handler.postDelayed(this, speedScroll.toLong())
//                    }
//                }
//            }
//            handler.postDelayed(runnable, speedScroll.toLong())
//
//        }

//        fun addBottomDots(context:Context,layout:LinearLayout,size:Int,currentPage: Int) {
//            val dots = arrayOfNulls<ImageView>(size)
//            layout.removeAllViews()
//            for (i in dots.indices) {
//                dots[i] = ImageView(context)
//                dots[i]?.setPadding(3,0,3,0)
//                if(i!=currentPage) {
//                    dots[i]!!.setImageDrawable(context.getDrawable(com.eumbrellacorp.richreach.R.drawable.icon_dot_normal))
//                }else{
//                    dots[i]!!.setImageDrawable(context.getDrawable(com.eumbrellacorp.richreach.R.drawable.icon_dot_active))
//                    dots[i]!!.animation= getFadeInAnimation(context)
//                }
//                layout.addView(dots[i])
//            }
//        }
















        fun setTextTheme(textView: TextView){

        }


//        fun getFadeInAnimation(context: Context?): Animation? {
//            return AnimationUtils.loadAnimation(context, com.eumbrellacorp.richreach.R.anim.fadein)
//        }



//        fun getShakeAnimation(context: Context?): Animation? {
//            return AnimationUtils.loadAnimation(context, com.eumbrellacorp.richreach.R.anim.shake)
//        }

//        fun getRecyclerVireItemScrollAnimation(
//            context: Context?,
//            position: Int,
//            lastPosition: Int
//        ): Animation? {
//            return AnimationUtils.loadAnimation(
//                context,
//                if (position > lastPosition) com.eumbrellacorp.richreach.R.anim.up_from_bottom else com.eumbrellacorp.richreach.R.anim.down_from_top
//            )
//        }

        fun expand(v: View) {
            val matchParentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
            val wrapContentMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
            val targetHeight = v.measuredHeight

            // Older versions of android (pre API 21) cancel animations for views with a height of 0.
            v.layoutParams.height = 1
            v.visibility = View.VISIBLE
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    v.layoutParams.height =
                        if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Expansion speed of 1dp/ms
            a.duration =
                (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight = v.measuredHeight
            val a: Animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    if (interpolatedTime == 1f) {
                        v.visibility = View.GONE
                    } else {
                        v.layoutParams.height =
                            initialHeight - (initialHeight * interpolatedTime).toInt()
                        v.requestLayout()
                    }
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }

            // Collapse speed of 1dp/ms
            a.duration =
                (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }





        fun rotateAnimation(view:View,degree:Float,listener:AnimationListener)
        {
            try {
                val rotate = RotateAnimation(
                    0F,
                    degree,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                rotate.duration = 300
                rotate.interpolator = LinearInterpolator()
                rotate.setAnimationListener(listener)

                view.startAnimation(rotate)
            }catch (e:Exception){

            }
        }

    }




}