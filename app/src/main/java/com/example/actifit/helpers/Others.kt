package com.example.actifit.helpers

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import java.text.SimpleDateFormat

class Others {

    //Loading Glide için kullanılacak.
    fun getCircularProgressDrawable(context: Context): CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        return circularProgressDrawable
    }

    fun convertDateMonthlyName(date: String): String {
        try {
            var spf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val newDate = spf.parse(date.toString())
            spf = SimpleDateFormat("dd MMMM yyyy")
            var date = spf.format(newDate)
            return date
        }
        catch (e:java.lang.Exception){
            return date
        }

    }


}