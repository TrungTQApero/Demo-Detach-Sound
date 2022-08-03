package com.apero.videoeditor.utils

import com.google.android.gms.ads.interstitial.InterstitialAd

class StorageCommon {

    var solutionInterstitial: InterstitialAd? = null
    fun interSolutionIsLoaded():Boolean = solutionInterstitial!=null

}