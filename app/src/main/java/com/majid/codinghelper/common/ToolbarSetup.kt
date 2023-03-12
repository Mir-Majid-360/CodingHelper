package com.majid.codinghelper.common

import android.app.Activity
import android.content.Context
import com.majid.codinghelper.databinding.ToolbarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ToolbarSetup {

    var binding: ToolbarBinding
    var context: Context

    constructor(
        binding: ToolbarBinding,
        context: Context,
    ) {
        this.binding = binding
        this.context = context

        initToolbarSetup()
    }

    private fun initToolbarSetup() {
        binding.tvTitle.text = "Coding Helper"
    }


}