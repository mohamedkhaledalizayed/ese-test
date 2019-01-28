package com.neqabty.presentation.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.neqabty.MainViewModel
import com.neqabty.presentation.ui.about.AboutViewModel
import com.neqabty.presentation.ui.claiming.ClaimingViewModel
import com.neqabty.presentation.ui.home.HomeViewModel
import com.neqabty.presentation.ui.login.LoginViewModel
import com.neqabty.presentation.ui.mobile.MobileViewModel
import com.neqabty.presentation.ui.news.NewsViewModel
import com.neqabty.presentation.ui.notifications.NotificationsViewModel
import com.neqabty.presentation.ui.signup.SignupViewModel
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesViewModel
import com.neqabty.presentation.ui.syndicates.SyndicatesViewModel
import com.neqabty.presentation.ui.trips.TripsViewModel
import com.neqabty.presentation.viewmodel.NeqabtyViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    abstract fun bindSignupViewModel(signupViewModel: SignupViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MobileViewModel::class)
    abstract fun bindMobileViewModel(mobileViewModel: MobileViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ClaimingViewModel::class)
    abstract fun bindClaimingViewModel(claimingViewModel: ClaimingViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SyndicatesViewModel::class)
    abstract fun bindSyndicatesViewModel(syndicatesViewModel: SyndicatesViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SubSyndicatesViewModel::class)
    abstract fun bindSubSyndicatesViewModel(subSyndicatesViewModel: SubSyndicatesViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAboutViewModel(aboutViewModel: AboutViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(newsViewModel: NewsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(TripsViewModel::class)
    abstract fun bindTripsViewModel(tripsViewModel: TripsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    abstract fun bindNotificationsViewModel(notificationsViewModel: NotificationsViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: NeqabtyViewModelFactory): ViewModelProvider.Factory
}
