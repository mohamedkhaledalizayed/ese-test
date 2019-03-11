package com.neqabty.presentation.di

import com.neqabty.presentation.ui.about.AboutFragment
import com.neqabty.presentation.ui.claiming.*
import com.neqabty.presentation.ui.home.HomeFragment
import com.neqabty.presentation.ui.login.LoginFragment
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoriesFragment
import com.neqabty.presentation.ui.medicalProviderDetails.MedicalProviderDetailsFragment
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersFragment
import com.neqabty.presentation.ui.mobile.MobileFragment
import com.neqabty.presentation.ui.news.NewsFragment
import com.neqabty.presentation.ui.newsDetails.NewsDetailsFragment
import com.neqabty.presentation.ui.notificationDetails.NotificationDetailsFragment
import com.neqabty.presentation.ui.notifications.NotificationsFragment
import com.neqabty.presentation.ui.signup.SignupFragment
import com.neqabty.presentation.ui.signup.SignupStep1Fragment
import com.neqabty.presentation.ui.signup.SignupStep2Fragment
import com.neqabty.presentation.ui.signup.SignupStep3Fragment
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesFragment
import com.neqabty.presentation.ui.syndicates.SyndicatesFragment
import com.neqabty.presentation.ui.tripDetails.TripDetailsFragment
import com.neqabty.presentation.ui.trips.TripsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSignupFragment(): SignupFragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep1Fragment(): SignupStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep2Fragment(): SignupStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep3Fragment(): SignupStep3Fragment

    @ContributesAndroidInjector
    abstract fun contributeMobileFragment(): MobileFragment

    @ContributesAndroidInjector
    abstract fun contributeClaimingFragment(): ClaimingFragment

    @ContributesAndroidInjector
    abstract fun contributeClaimingStep1Fragment(): ClaimingStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeClaimingStep2Fragment(): ClaimingStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeClaimingStep3Fragment(): ClaimingStep3Fragment

    @ContributesAndroidInjector
    abstract fun contributeClaimingStep4Fragment(): ClaimingStep4Fragment

    @ContributesAndroidInjector
    abstract fun contributeSyndicatesFragment(): SyndicatesFragment

    @ContributesAndroidInjector
    abstract fun contributeSubSyndicatesFragment(): SubSyndicatesFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeTripsFragment(): TripsFragment

    @ContributesAndroidInjector
    abstract fun contributeTripDetailsFragment(): TripDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDetailsFragment(): NewsDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsDetailsFragment(): NotificationDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalCategoriesFragment(): MedicalCategoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProvidersFragment(): MedicalProvidersFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProviderDetailsFragment(): MedicalProviderDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}