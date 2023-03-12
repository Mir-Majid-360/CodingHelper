package com.majid.codinghelper.common

import android.graphics.Typeface

interface IListeners {


        abstract class IClickListener{
             open fun onItemClicked(model:Any, pos:Int){}

          }

      abstract class  ISharedPreferenceListener{
        fun onPreferenceChanged(key:String){}
     }

    abstract  class  IClickListeners{
     open   fun onItemClicked(model: Any,pos: Int){

        }

        open     fun onItemLongPressed(model: Any,pos: Int){

        }
    }

    abstract  class  IFontDownloadListener{

        fun onFontDownloaded(typeface: Typeface){}
        fun fontDownloadFailed(msg:String){}
    }







}