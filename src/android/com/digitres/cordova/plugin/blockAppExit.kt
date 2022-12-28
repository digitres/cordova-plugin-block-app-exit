package com.digitres.cordova.plugin

import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.content.DialogInterface
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaWebView
import org.apache.cordova.PluginResult

import android.content.Context
import android.content.Intent

import android.util.Log
import android.view.Window
import android.view.View
import android.view.WindowManager;
import android.app.ActionBar
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import android.content.ComponentName
import android.app.ActivityManager
import android.content.*


class BlockAppExit: CordovaPlugin(){

    override protected fun pluginInitialize() {
        // Intercept window events
//        try {
//            val win: Window = this.cordova.getActivity().getWindow()
//            val existingCallback: Window.Callback = win.getCallback()
//            win.setCallback(blockAppWindowCallback(existingCallback, this::onWindowFocusChanged))
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            this.alert("ELK Alert: Error initialising blockAppExitPlugin: \n", e.toString(), "Close")
//        }

    }


    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        try {
          //  this.eventsContext = callbackContext
            when (action){
                "enable" -> {
                    enableBlockAppExit(callbackContext)
                    return true
                }
               "disable" -> {
                     callbackContext.success("Block App exit disabled")
                   return true
               }
                else ->{
                    echo("Unknown action in BlockAppExit Plugin execute function: $action",callbackContext)
                    return false
                }
            }
        } catch (ex: Exception){
            callbackContext.error("Error in BlockAppExit Plugin execute function: ${ex.message}");
            return false
        }

    }

    private fun onWindowFocusChanged(hasFocus: Boolean?) {

        if (hasFocus!! == false) { // trape if trying to close window
            var appContext: Context = this.cordova.getActivity().getApplicationContext()

            try {
               // this.alert("ELK Alert", "Window losing focus", "Close")

                val windowCloseExecutor = ContextCompat.getMainExecutor(appContext)
                windowCloseExecutor.execute {
                    val am = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                    val cn = am.getRunningTasks(1)[0].topActivity
                    if (cn != null && cn.className == "com.android.systemui.recent.RecentsActivity") {
                        val closeRecents = Intent("com.android.systemui.recent.action.TOGGLE_RECENTS")
                        closeRecents.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                        val recents =
                            ComponentName("com.android.systemui", "com.android.systemui.recent.RecentsActivity")
                        closeRecents.component = recents
                        appContext.startActivity(closeRecents)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                this.alert("ELK Alert", e.toString(), "Close")
            }
        }
    }


    override fun onPause(multitasking: Boolean ) {
      //  this.alert("ELK Alert", "App paused", "Close")
        var appContext: Context = this.cordova.getActivity().getApplicationContext()
        val am: ActivityManager = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var taskId = this.cordova.getActivity().getTaskId()
        am.moveTaskToFront(taskId, 0)
    }


    private fun enableBlockAppExit(callbackContext: CallbackContext){
        try {
            val win: Window = this.cordova.getActivity().getWindow()
            val existingCallback: Window.Callback = win.getCallback()
            win.setCallback(blockAppWindowCallback(existingCallback, this::onWindowFocusChanged))
            callbackContext.success("Block App exit enabled")
        } catch (ex: Exception) {
            callbackContext.error("Failed to enable block: ${ex.message}");
        }
    }

    private fun echo(
        message: String= "Test echo message",
        callbackContext: CallbackContext
    ) {
        if (message.isNotEmpty()) {
            callbackContext.success(message)
        } else {
            callbackContext.error("Expected one non-empty string argument.")
        }
    }


    @kotlin.jvm.Synchronized
    fun alert(
        title: String,
        message: String,
        buttonLabel: String,
    ) {
        Builder(cordova.getActivity())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNeutralButton(buttonLabel){dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}