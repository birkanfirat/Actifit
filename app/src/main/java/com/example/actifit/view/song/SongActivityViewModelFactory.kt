package com.example.actifit.view.song

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actifit.base.BaseActivity

class SongActivityViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongsActivityViewModel::class.java)) {
            return SongsActivityViewModel(
                activity = BaseActivity().getActivity()!!
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}