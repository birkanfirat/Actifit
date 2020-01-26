package com.example.actifit.view

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        @JvmStatic
        var mContext: Context? = null

        fun getContext(): Context? {
            return mContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext

    }
}