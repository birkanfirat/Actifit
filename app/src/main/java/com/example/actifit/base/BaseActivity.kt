package com.example.actifit.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {



    fun getActivityContext(): Context? {
        return baseContext
    }
    fun getActivity(): Activity? {
        return this@BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    fun hideSoftKeyboard() {
        val inputMethodManager = this.getSystemService(
            Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            this.getCurrentFocus().getWindowToken(), 0)
        /*    if (currentFocus != null) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager!!.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)*/
    }
    fun hideSoftKeyboard(edt: EditText) {
        val inputMethodManager = this.getSystemService(
            Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            edt.getWindowToken(), 0)
        /*    if (currentFocus != null) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager!!.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)*/
    }

}
