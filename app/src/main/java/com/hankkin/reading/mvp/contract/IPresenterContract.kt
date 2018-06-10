package com.hankkin.reading.mvp.contract

/**
 * Created by huanghaijie on 2018/5/16.
 */
interface IPresenterContract {

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun registerMvpView(mvpView: IBaseViewContract)
}