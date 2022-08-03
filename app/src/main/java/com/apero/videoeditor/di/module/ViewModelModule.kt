package com.apero.videoeditor.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apero.videoeditor.di.ViewModelFactory
import com.apero.videoeditor.di.key.ViewModelKey
import com.apero.videoeditor.ui.home.DecodeViewModel
import com.apero.videoeditor.ui.home.HomeViewModel
import com.apero.videoeditor.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Furkan on 2019-10-16
 */

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    abstract fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(DecodeViewModel::class)
    abstract fun provideDecodeViewModel(decodeViewModel: DecodeViewModel): ViewModel

}
