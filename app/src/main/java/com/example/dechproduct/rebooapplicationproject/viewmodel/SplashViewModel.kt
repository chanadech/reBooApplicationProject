package com.example.dechproduct.rebooapplicationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel(){
    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()
    init {
        GlobalScope.launch {
            delay(3000)
            mutableLiveData.postValue(SplashState.MainActivity())
        }
    }
}

sealed class SplashState {
    class MainActivity : SplashState()
}

//finish แล้วจ้า ไปเริ่มที่ Main Activity เลย