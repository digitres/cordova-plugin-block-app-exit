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
import androidx.fragment.app.Fragment
import com.digitres.cordova.plugin.BlockActivity

class BlockAppExit: CordovaPlugin(){
    private var exitAllowed: Boolean = true
    private val context: Context = this.cordova.getActivity().getApplicationContext()

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
//        when (action) {
//            "disable" -> {
//                val newIntent = Intent(context, BlockActivity::class.java)
//                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                context.startActivity(newIntent)
//                exitAllowed = false
//                echo("App exit disabled", callbackContext)
//            }
//            "enable" -> {
//                exitAllowed = true
//                echo("App exit enabled", callbackContext)
//            }
//            else -> {
//                callbackContext.error("Error from plugin")
//            }
//        }
        callbackContext.success("Response from Plugin");
        return true
    }

    private fun echo(
        message: String,
        callbackContext: CallbackContext
    ) {
        if (message.isNotEmpty()) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

}