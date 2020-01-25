package com.example.actifit.base

import android.content.Context
import android.content.ContextWrapper
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun getBaseActivity(): BaseActivity {
        var context = context
        while (context is ContextWrapper) {
            if (context is BaseActivity) {
                return context
            }
            context = context.baseContext
        }
        return BaseActivity()
    }

    fun getActivityContext(): Context? {
        return getBaseActivity()
    }
}