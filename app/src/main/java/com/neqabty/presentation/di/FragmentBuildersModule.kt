package com.neqabty.presentation.di

import com.neqabty.presentation.ui.about.AboutFragment
import com.neqabty.presentation.ui.aboutApp.AboutAppFragment
import com.neqabty.presentation.ui.activateAccount.ActivateAccountFragment
import com.neqabty.presentation.ui.addCompanion.AddCompanionFragment
import com.neqabty.presentation.ui.changePassword.ChangePasswordFragment
import com.neqabty.presentation.ui.changeUserMobile.ChangeUserMobileFragment
import com.neqabty.presentation.ui.chooseArea.ChooseAreaFragment
import com.neqabty.presentation.ui.claiming.*
import com.neqabty.presentation.ui.complaint.ComplaintFragment
import com.neqabty.presentation.ui.corona.CoronaFragment
import com.neqabty.presentation.ui.engineeringRecordsDetails.EngineeringRecordsDetailsFragment
import com.neqabty.presentation.ui.engineeringRecordsInquiry.EngineeringRecordsInquiryFragment
import com.neqabty.presentation.ui.favorites.FavoritesFragment
import com.neqabty.presentation.ui.forgetPassword.ForgetPasswordFragment
import com.neqabty.presentation.ui.home.*
import com.neqabty.presentation.ui.inquireMedicalLetters.InquireMedicalLettersFragment
import com.neqabty.presentation.ui.inquiry.InquiryFragment
import com.neqabty.presentation.ui.inquiryDetails.InquiryDetailsFragment
import com.neqabty.presentation.ui.intro.IntroFragment
import com.neqabty.presentation.ui.login.LoginFragment
import com.neqabty.presentation.ui.loginWithPassword.LoginWithPasswordFragment
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoriesFragment
import com.neqabty.presentation.ui.medicalLetters.MedicalLettersFragment
import com.neqabty.presentation.ui.medicalMain.MedicalMainFragment
import com.neqabty.presentation.ui.medicalProfessions.MedicalProfessionsFragment
import com.neqabty.presentation.ui.medicalProviderDetails.MedicalProviderDetailsFragment
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersFragment
import com.neqabty.presentation.ui.medicalRenew.MedicalRenewFragment
import com.neqabty.presentation.ui.medicalRenewDetails.MedicalRenewDetailsFragment
import com.neqabty.presentation.ui.medicalRenewFollowerDetails.MedicalRenewAddFollowerDetailsFragment
import com.neqabty.presentation.ui.medicalRenewFollowerDetails.MedicalRenewFollowerDetailsFragment
import com.neqabty.presentation.ui.medicalRenewUpdate.MedicalRenewUpdateFragment
import com.neqabty.presentation.ui.news.NewsFragment
import com.neqabty.presentation.ui.newsDetails.NewsDetailsFragment
import com.neqabty.presentation.ui.notificationDetails.NotificationDetailsFragment
import com.neqabty.presentation.ui.notifications.NotificationsFragment
import com.neqabty.presentation.ui.notifications.NotificationsListFragment
import com.neqabty.presentation.ui.oldsignup.SignupFragment
import com.neqabty.presentation.ui.oldsignup.SignupStep1Fragment
import com.neqabty.presentation.ui.oldsignup.SignupStep2Fragment
import com.neqabty.presentation.ui.oldsignup.SignupStep3Fragment
import com.neqabty.presentation.ui.onlinePharmacy.OnlinePharmacyFragment
import com.neqabty.presentation.ui.payment.PaymentFragment
import com.neqabty.presentation.ui.phones.PhonesFragment
import com.neqabty.presentation.ui.search.SearchFragment
import com.neqabty.presentation.ui.settings.SettingsFragment
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesFragment
import com.neqabty.presentation.ui.syndicates.SyndicatesFragment
import com.neqabty.presentation.ui.trackShipment.TrackShipmentFragment
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
    abstract fun contributeIntroFragment(): IntroFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginWithPasswordFragment(): LoginWithPasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeOldSignupFragment(): SignupFragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep1Fragment(): SignupStep1Fragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep2Fragment(): SignupStep2Fragment

    @ContributesAndroidInjector
    abstract fun contributeSignupStep3Fragment(): SignupStep3Fragment

    @ContributesAndroidInjector
    abstract fun contributeSignupFragment(): com.neqabty.presentation.ui.signup.SignupFragment

    @ContributesAndroidInjector
    abstract fun contributeActivateAccountFragment(): ActivateAccountFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeForgetPasswordFragment(): ForgetPasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeTrackShipmentFragment(): TrackShipmentFragment

    @ContributesAndroidInjector
    abstract fun contributeChangeUserMobileFragment(): ChangeUserMobileFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalLettersFragment(): MedicalLettersFragment

    @ContributesAndroidInjector
    abstract fun contributeInquireMedicalLettersFragment(): InquireMedicalLettersFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalRenewFragment(): MedicalRenewFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalRenewDetailsFragment(): MedicalRenewDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalRenewUpdateFragment(): MedicalRenewUpdateFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalRenewFollowerDetailsFragment(): MedicalRenewFollowerDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalRenewAddFollowerDetailsFragment(): MedicalRenewAddFollowerDetailsFragment

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
    abstract fun contributeOnlinePharmacyFragment(): OnlinePharmacyFragment

    @ContributesAndroidInjector
    abstract fun contributeSyndicatesFragment(): SyndicatesFragment

    @ContributesAndroidInjector
    abstract fun contributeSubSyndicatesFragment(): SubSyndicatesFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    abstract fun contributeAboutAppFragment(): AboutAppFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

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
    abstract fun contributeMedicalMainFragment(): MedicalMainFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProvidersFragment(): MedicalProvidersFragment

    @ContributesAndroidInjector
    abstract fun contributeMedicalProviderDetailsFragment(): MedicalProviderDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributePhonesFragment(): PhonesFragment

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
    abstract fun contributeWheelNewsFragment(): WheelNewsFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelTripsFragment(): WheelTripsFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelPaymentsFragment(): WheelPaymentsFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelRetireesFragment(): WheelRetireesFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelTrainingFragment(): WheelTrainingFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelComplaintsFragment(): WheelComplaintsFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelEmploymentFragment(): WheelEmploymentFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelSyndicateServicesFragment(): WheelSyndicateServicesFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelCustomerServiceFragment(): WheelCustomerServiceFragment

    @ContributesAndroidInjector
    abstract fun contributeWheelMedicalFragment(): WheelMedicalFragment

    @ContributesAndroidInjector
    abstract fun contributeComplaintFragment(): ComplaintFragment

    @ContributesAndroidInjector
    abstract fun contributeCoronaFragment(): CoronaFragment
}
