package com.digitres.cordova.plugin

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;
import android.widget.*;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import java.lang.Integer;
import java.util.Collections;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

class BlockActivity: CordovaActivity() {


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            System.out.println("Has Focus");
           // callbackContext.success("Has focus");
            //  Toast.makeText(context, "Has focus", Toast.LENGTH_SHORT).show()
        } else {
            System.out.println("Focus changed");
          //  callbackContext.success("Focus changed");
            //  Toast.makeText(context, "Focus changed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        val activityManager: ActivityManager = getApplicationContext()
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(getTaskId(), 0)
    }

}