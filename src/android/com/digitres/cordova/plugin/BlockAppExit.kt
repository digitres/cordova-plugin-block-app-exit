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
import androidx.core.app.NotificationManagerCompat
import androidx.activity.ComponentActivity
import android.content.ComponentName
import android.app.ActivityManager
import android.content.*

import java.util.Timer;
import java.util.TimerTask;

class BlockAppExit: CordovaPlugin(){
    @kotlin.jvm.Volatile
    var closeSystemDialogIntervalMillis: Long = 200
    @kotlin.jvm.Volatile
    var closeSystemDialogDurationMillis: Long = 20000
    @kotlin.jvm.Volatile
    var enabled: Boolean = false
    private var globalCallbackContext: CallbackContext? = null

//    override fun initialize(cordova: CordovaInterface?, webView: CordovaWebView?) {
//        super.initialize(cordova, webView)
//        enableBlockAppExit()
//    }


    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        try {
            this.globalCallbackContext = callbackContext
            when (action){
                "enable" -> {
                    if (android.os.Build.VERSION.SDK_INT >= 24) { // Android 7
                        enableBlockAppExit()
                        callbackContext.success("Block App exit enabled")
                    } else {
                        callbackContext.error("Block App Exit plugin works for android 7+")
                    }
                    return true
                }
                "disable" -> {
                    this.enabled = false
                    callbackContext.success("Block App exit disabled")
                    return true
                }
                "echo" -> {
                    echo("echo from Plugin",callbackContext)
                    return true
                }
                else ->{
                    echo("Unknown action in BlockAppExit Plugin execute function: $action",callbackContext)
                    return false
                }
            }
        } catch (ex: Exception){
            callbackContext.error("BlockAppExit Plugin error: ${ex.message}");
            return false
        }

    }

    private fun onWindowFocusChanged(hasFocus: Boolean?) {
        if (!hasFocus!! && this.enabled) { // trape if trying to close window
            try {
                closeSystemDialogs()
            } catch (e: Exception) {
                e.printStackTrace()
                this.alert("ELK Alert", e.toString(), "Close")
            }
        }
    }


    override fun onPause(multitasking: Boolean ) {
        super.onPause(multitasking)
        if (this.enabled) {
            var appContext: Context = this.cordova.getActivity().getApplicationContext()
            val am: ActivityManager = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            var taskId = this.cordova.getActivity().getTaskId()
            am.moveTaskToFront(taskId, 0)
        }
    }

    override fun onResume(multitasking: Boolean ){
        super.onResume(multitasking)
        if (android.os.Build.VERSION.SDK_INT >= 24) { // Android 7
            enableBlockAppExit()
            this.globalCallbackContext?.success("Block App exit enabled")
        } else {
            this.globalCallbackContext?.error("Block App Exit plugin works for android 7+")
        }
    }

    fun closeSystemDialogs(){
        var appContext: Context = this.cordova.getActivity().getApplicationContext()
        NotificationManagerCompat.from(appContext).cancelAll()
        val am: ActivityManager = appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        am.moveTaskToFront(this.cordova.getActivity().getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME)
        val timer = Timer()
        val begin : Long = 0
        val timeInterval: Long = this.closeSystemDialogIntervalMillis
        val duration: Long = this.closeSystemDialogDurationMillis
        timer.schedule(object: TimerTask() {
            var counter = 0
            override fun run() {
                //call the method
                NotificationManagerCompat.from(appContext).cancelAll()
                counter++
                // run for x seconds
                if (counter * timeInterval >= duration) {
                    timer.cancel()
                }
            }
        }, begin, timeInterval)
    }

    private fun enableBlockAppExit(){
        try {
            this.enabled  = true
            val win: Window = this.cordova.activity.window
            val existingCallback: Window.Callback = win.callback
            win.callback = BlockAppWindowCallback(existingCallback, this::onWindowFocusChanged)

        } catch (e: Exception) {
            e.printStackTrace()
            this.alert("ELK Block-Exit-Alert", e.toString(), "Close")
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