package com.majid.codinghelper.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.majid.codinghelper.R
import com.majid.codinghelper.auth.ui.AuthActivity
import com.majid.codinghelper.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding


    /**
     * In Update Feature
     * **/
    private lateinit var appUpdateManager: AppUpdateManager
    private  val updateType = AppUpdateType.IMMEDIATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        if (updateType ==AppUpdateType.FLEXIBLE){
            appUpdateManager.registerListener(installUpdateSuccessListener)
        }
        checkAppForUpdates()
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        //Normal Handler is deprecated , so we have to change the code little bit

        // Handler().postDelayed({
        Handler(Looper.getMainLooper()).postDelayed({

            if (true){
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000) //
    }




    private  val installUpdateSuccessListener = InstallStateUpdatedListener{state->
        if (state.installStatus() ==InstallStatus.DOWNLOADED){
            Toast.makeText(applicationContext,"Download successful. Restarting app in 5 seconds",
                Toast.LENGTH_LONG)
                .show()
            lifecycleScope.launch {

                delay(5.seconds)
                appUpdateManager.completeUpdate()
            }
        }

    }
    private  fun checkAppForUpdates(){
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info->

            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed =  when(updateType){
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable && isUpdateAllowed){
              appUpdateManager.startUpdateFlowForResult(
                  info,
                  updateType,
                  this,
                  123
              )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE) {


            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {

                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        this,
                        123
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 123){
            if (requestCode != RESULT_OK){
                println("Something Went Wrong updating....")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.unregisterListener(installUpdateSuccessListener)
        }
    }
}