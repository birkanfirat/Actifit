package com.example.actifit.helpers

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.actifit.R
import com.example.actifit.base.BaseActivity

class Router {

    fun addFragment(activity: BaseActivity?, fragment: Fragment?) {

        if (fragment != null) {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.addToBackStack(null)?.commit()
        }

    }
    fun addFragmentReplace(activity: BaseActivity?, fragment: Fragment?) {

        if (fragment != null) {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.commit()
        }

    }
    fun pageIntent(context: Context?, activity2: BaseActivity) {
        val i = Intent(context, activity2::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)
    }


}
