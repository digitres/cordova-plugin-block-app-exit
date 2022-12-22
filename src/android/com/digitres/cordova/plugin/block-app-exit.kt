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

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
            // blockExit()
            echo("Message from Plugin",callbackContext)
            return true
        }

    // this just brings the current activity back to the front hence preventing user from leaving
//    private fun blockExit() {
//        val activityManager = applicationContext
//            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        activityManager.moveTaskToFront(taskId, 0)
//    }


    private fun echo(
        message: String= "Test echo message",
        callbackContext: CallbackContext
    ) {
        if (message.isNotEmpty()) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}