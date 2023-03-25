package com.majid.codinghelper.auth.authviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.majid.codinghelper.utils.Event

class AuthViewModel() : ViewModel() {


    private var openSignUpFragment: MutableLiveData<Event<Array<Any>>> =
        MutableLiveData<Event<Array<Any>>>()

    private var openLoginFragment: MutableLiveData<Event<Array<Any>>> =
        MutableLiveData<Event<Array<Any>>>()


    fun getOpenSignUpFragment(): MutableLiveData<Event<Array<Any>>> {
        return openSignUpFragment
    }

    fun setOpenSignUpFragment(objects: Array<Any>) {
        openSignUpFragment.value = Event(objects)
    }


    fun getOpenLoginFragment(): MutableLiveData<Event<Array<Any>>> {
        return openLoginFragment
    }

    fun setOpenLoginFragment(objects: Array<Any>) {
        openLoginFragment.value = Event(objects)
    }
}
