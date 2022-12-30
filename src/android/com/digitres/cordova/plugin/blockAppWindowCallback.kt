package com.digitres.cordova.plugin


import android.util.Log
import android.view.ActionMode
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import java.util.function.Consumer

class blockAppWindowCallback(
    existingCallback: Window.Callback,
    windowFocusChangedCallback: Consumer<Boolean?>
) : Window.Callback {
    protected var existingCallback: Window.Callback
    protected var onWindowFocusedChangedCallback: Consumer<Boolean?>

    init {
        this.existingCallback = existingCallback
        onWindowFocusedChangedCallback = windowFocusChangedCallback
    }

   override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        return existingCallback.dispatchKeyEvent(event)
    }

    override fun dispatchKeyShortcutEvent(event: KeyEvent?): Boolean {
        return existingCallback.dispatchKeyShortcutEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        return existingCallback.dispatchTouchEvent(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent?): Boolean {
        return existingCallback.dispatchTrackballEvent(event)
    }

    override fun dispatchGenericMotionEvent(event: MotionEvent?): Boolean {
        return existingCallback.dispatchGenericMotionEvent(event)
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        return existingCallback.dispatchPopulateAccessibilityEvent(event)
    }

    override fun onCreatePanelView(featureId: Int): View? {
        return existingCallback.onCreatePanelView(featureId)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        return existingCallback.onCreatePanelMenu(featureId, menu)
    }

    override fun onPreparePanel(featureId: Int, view: View?, menu: Menu): Boolean {
        return existingCallback.onPreparePanel(featureId, view, menu)
    }

    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        return existingCallback.onMenuOpened(featureId, menu)
    }

    override fun onMenuItemSelected(featureId: Int, item: MenuItem): Boolean {
        return existingCallback.onMenuItemSelected(featureId, item)
    }

    override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams?) {
        existingCallback.onWindowAttributesChanged(attrs)
    }

    override fun onContentChanged() {
        existingCallback.onContentChanged()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        onWindowFocusedChangedCallback.accept(hasFocus)
        existingCallback.onWindowFocusChanged(hasFocus)
    }

    override fun onAttachedToWindow() {
        existingCallback.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        existingCallback.onDetachedFromWindow()
    }

    override fun onPanelClosed(featureId: Int, menu: Menu) {
        existingCallback.onPanelClosed(featureId, menu)
    }

    override fun onSearchRequested(): Boolean {
        return existingCallback.onSearchRequested()
    }

    override fun onSearchRequested(searchEvent: SearchEvent?): Boolean {
        return existingCallback.onSearchRequested(searchEvent)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?): ActionMode? {
        return existingCallback.onWindowStartingActionMode(callback)
    }

    override fun onWindowStartingActionMode(callback: ActionMode.Callback?, type: Int): ActionMode? {
        return existingCallback.onWindowStartingActionMode(callback, type)
    }

    override fun onActionModeStarted(mode: ActionMode?) {
        existingCallback.onActionModeStarted(mode)
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        existingCallback.onActionModeFinished(mode)
    }
}