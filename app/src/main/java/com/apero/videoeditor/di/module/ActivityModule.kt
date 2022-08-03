package com.apero.videoeditor.di.module

import com.apero.videoeditor.di.scope.PerActivity
import com.apero.videoeditor.ui.home.DecodeActivity
import com.apero.videoeditor.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Furkan on 2019-10-16
 */

@Module
abstract class ActivityModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun decodeActivity(): DecodeActivity
}
