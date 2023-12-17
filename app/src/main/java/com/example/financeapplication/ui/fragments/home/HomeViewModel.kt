package com.example.financeapplication.ui.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entities.DataUtils


class HomeViewModel : ViewModel() {

    init {
        Log.e("data", DataUtils.user?.value?.fullName.toString() )
    }




}