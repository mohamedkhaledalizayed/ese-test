package com.neqabty.presentation.di

import com.neqabty.presentation.ui.about.AboutFragment
import com.neqabty.presentation.ui.addCompanion.AddCompanionFragment
import com.neqabty.presentation.ui.chooseArea.ChooseAreaFragment
import com.neqabty.presentation.ui.claiming.*
import com.neqabty.presentation.ui.complaint.ComplaintFragment
import com.neqabty.presentation.ui.engineeringRecordsDetails.EngineeringRecordsDetailsFragment
import com.neqabty.presentation.ui.engineeringRecordsInquiry.EngineeringRecordsInquiryFragment
import com.neqabty.presentation.ui.favorites.FavoritesFragment
import com.neqabty.presentation.ui.home.HomeFragment
import com.neqabty.presentation.ui.inquiry.InquiryFragment
import com.neqabty.presentation.ui.inquiryDetails.InquiryDetailsFragment
import com.neqabty.presentation.ui.login.LoginFragment
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoriesFragment
import com.neqabty.presentation.ui.medicalProfessions.MedicalProfessionsFragment
import com.neqabty.presentation.ui.medicalProviderDetails.MedicalProviderDetailsFragment
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersFragment
import com.neqabty.presentation.ui.mobile.MobileFragment
import com.neqabty.presentation.ui.news.NewsFragment
import com.neqabty.presentation.ui.newsDetails.NewsDetailsFragment
import com.neqabty.presentation.ui.notificationDetails.NotificationDetailsFragment
import com.neqabty.presentation.ui.notifications.NotificationsFragment
import com.neqabty.presentation.ui.notifications.NotificationsListFragment
import com.neqabty.presentation.ui.payment.PaymentFragment
import com.neqabty.presentation.ui.search.SearchFragment
import com.neqabty.presentation.ui.signup.SignupFragment
import com.neqabty.presentation.ui.signup.SignupStep1Fragment
import com.neqabty.presentation.ui.signup.SignupStep2Fragment
import com.neqabty.presentation.ui.signup.SignupStep3Fragment
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesFragment
import com.neqabty.presentation.ui.syndicates.SyndicatesFragment
import com.neqabty.presentation.ui.tripDetails.TripDetailsFragment
import com.neqabty.presentation.ui.trips.TripsFragment
import com.neqabty.presentation.ui.tripsReservation.TripReservationFragment
import com.neqabty.presentation.ui.updateData.UpdateDataFragment
import com.neqabty.presentation.ui.updateDataDetails.UpdateDataDetailsFragment
import com.neqabty.presentation.ui.updateDataVerification.UpdateDataVerificationFragment
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
    abstract fun contributeTripReservationFragment(): TripReservationFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCompanionFragment(): AddCompanionFragment

    @ContributesAndroidInjector
    abstract fun contributePaymentFragment(): PaymentFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsDetailsFragment(): NewsDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsListFragment(): NotificationsListFragment

    @ContributesAndroidInjector
    abstract fun contributeNotificationsDetailsFragment(): NotificationDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeChooseAreaFragment(): ChooseAreaFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProfessionsFragment(): MedicalProfessionsFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalCategoriesFragment(): MedicalCategoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProvidersFragment(): MedicalProvidersFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProviderDetailsFragment(): MedicalProviderDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateDataFragment(): UpdateDataFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateDataDetailsFragment(): UpdateDataDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeUpdateDataVerificationFragment(): UpdateDataVerificationFragment

    @ContributesAndroidInjector
    abstract fun contributeEngineeringRecordsInquiryFragment(): EngineeringRecordsInquiryFragment

    @ContributesAndroidInjector
    abstract fun contributeEngineeringRecordsDetailsFragment(): EngineeringRecordsDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeInquiryFragment(): InquiryFragment

    @ContributesAndroidInjector
    abstract fun contributeInquiryDetailsFragment(): InquiryDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoritesFragment(): FavoritesFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeComplaintFragment(): ComplaintFragment
}
