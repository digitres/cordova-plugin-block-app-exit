package com.digitres.cordova.plugin
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


import android.app.ActivityManager
import android.content.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
//import org.koin.androidx.viewmodel.ext.android.viewModel


class BlockAppExit: CordovaPlugin(){
    private exitAllowed: bool = true
    private context: CallbackContext
    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
            // blockExit()
            context = callbackContext
            if (action == 'disable') {
                blockEnabled = true
                echo("App exit disabled",callbackContext)
            } else if (action == 'enable') {
                blockEnabled = false
                echo("App exit enabled",callbackContext)
            }

            return true
        }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            Toast.makeText(BlockAppExit, "Has focus", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(BlockAppExit, "Focus changed", Toast.LENGTH_SHORT).show()
        }

//        if (!hasFocus) {
//            val windowCloseExecutor = ContextCompat.getMainExecutor(this)
//            windowCloseExecutor.execute {
//                val am = applicationContext.getSystemService(ACTIVITY_SERVICE) as ActivityManager
//                val cn = am.getRunningTasks(1)[0].topActivity
//                if (cn != null && cn.className == "com.android.systemui.recent.RecentsActivity") {
//                    blockRecentButton()
//                }
//            }
//        }
    }


//    override fun onPause() {
//        super.onPause()
//        val activityManager = applicationContext
//            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        activityManager.moveTaskToFront(taskId, 0)
//    }


    private fun echo(
        message: String,
        callbackContext: CallbackContext
    ) {
        Toast.makeText(context, "Echo from Plugin", Toast.LENGTH_SHORT).show();
        if (message.isNotEmpty()) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}