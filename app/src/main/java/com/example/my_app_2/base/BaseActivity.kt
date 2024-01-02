package com.example.my_app_2.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes val id: Int) : AppCompatActivity(),
    CoroutineScope {

    private var useBus = false
    lateinit var binding: T


    abstract fun init(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {

        requestedOrientation = if (true) {
            ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_USER
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window?.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        super.onCreate(savedInstanceState)
//        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

        binding = DataBindingUtil.setContentView(this@BaseActivity, id) as T
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        useBus = init()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (window != null)
            if (window?.currentFocus != null)
                window?.currentFocus?.clearFocus()
        super.onBackPressed()
    }

    override fun onStart() {
        super.onStart()
//        if (useBus) {
//            EventBus.getDefault().register(this)
//        }
    }

    override fun onStop() {
//        if (useBus) {
//
//        }
//            EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onPause() {
        if (window != null)
            if (window.currentFocus != null)
                window?.currentFocus?.clearFocus()
        super.onPause()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase)
    }

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
