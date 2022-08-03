package com.apero.videoeditor

import com.ads.control.Admod
import com.ads.control.AdsMultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.apero.videoeditor.di.AppInjector
import com.apero.videoeditor.utils.SharePreferencesManager
import com.apero.videoeditor.utils.StorageCommon
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class App : AdsMultiDexApplication(), HasAndroidInjector {

    var storageCommon: StorageCommon? = null

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        Fresco.initialize(this)
        SharePreferencesManager.initializeInstance(this)
        initAds()
        app = this
        storageCommon = StorageCommon()
    }

    private fun initAds() {
        Admod.getInstance().setOpenActivityAfterShowInterAds(true)
        Admod.getInstance().setFan(true)
    }

    override fun enableAdsResume(): Boolean = false

    override fun getListTestDeviceId(): MutableList<String> {
        val listDeviceTest = mutableListOf<String>()
        listDeviceTest.add("09B0EE72136D265381B7A5694CD78D23")
        return listDeviceTest
    }

    override fun getOpenAppAdId(): String {
        return BuildConfig.ad_appopen_resume
    }

    override fun enableAdjust(): Boolean = false

    override fun getAdjustToken(): String = ""

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    companion object {
        lateinit var app: App
        fun getInstance(): App {
            return app
        }
    }
}