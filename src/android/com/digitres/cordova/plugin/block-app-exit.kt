package com.digitres.cordova.plugin
import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
//import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.Toast
import com.digitres.cordova.plugin.BlockActivity

class BlockAppExit: CordovaPlugin(){
    private var exitAllowed: Boolean = true
    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
            // blockExit()
            if (action.equals("disable")) {
                val context: ActivityManager = getApplicationContext()
                val newIntent = Intent(context, BlockActivity::class.java)
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(newIntent)
                exitAllowed = false
                echo("App exit disabled",callbackContext)
            } else if (action.equals("enable")) {
                exitAllowed = true
                echo("App exit enabled",callbackContext)
            } else  {
                echo("Echo from plugin", callbackContext)
            }

            return true
        }

    private fun echo(
        message: String,
        callbackContext: CallbackContext
    ) {
        System.out.println("Entering echo")
       // Toast.makeText(context, "Echo from Plugin", Toast.LENGTH_SHORT).show();
        if (message.isNotEmpty()) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

}