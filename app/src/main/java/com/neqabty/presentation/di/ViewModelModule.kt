package com.neqabty.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neqabty.MainViewModel
import com.neqabty.presentation.ui.about.AboutViewModel
import com.neqabty.presentation.ui.activateAccount.ActivateAccountViewModel
import com.neqabty.presentation.ui.changePassword.ChangePasswordViewModel
import com.neqabty.presentation.ui.chooseArea.ChooseAreaViewModel
import com.neqabty.presentation.ui.claiming.ClaimingViewModel
import com.neqabty.presentation.ui.complaint.ComplaintViewModel
import com.neqabty.presentation.ui.corona.CoronaViewModel
import com.neqabty.presentation.ui.engineeringRecordsDetails.EngineeringRecordsDetailsViewModel
import com.neqabty.presentation.ui.engineeringRecordsInquiry.EngineeringRecordsInquiryViewModel
import com.neqabty.presentation.ui.favorites.FavoritesViewModel
import com.neqabty.presentation.ui.home.HomeViewModel
import com.neqabty.presentation.ui.inquiry.InquiryViewModel
import com.neqabty.presentation.ui.inquiryDetails.InquiryDetailsViewModel
import com.neqabty.presentation.ui.login.LoginViewModel
import com.neqabty.presentation.ui.loginWithPassword.LoginWithPasswordViewModel
import com.neqabty.presentation.ui.medicalProfessions.MedicalProfessionsViewModel
import com.neqabty.presentation.ui.medicalProviderDetails.MedicalProviderDetailsViewModel
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersViewModel
import com.neqabty.presentation.ui.medicalRenew.MedicalRenewViewModel
import com.neqabty.presentation.ui.medicalRenewDetails.MedicalRenewDetailsViewModel
import com.neqabty.presentation.ui.medicalRenewUpdate.MedicalRenewUpdateViewModel
import com.neqabty.presentation.ui.news.NewsViewModel
import com.neqabty.presentation.ui.notificationDetails.NotificationDetailsViewModel
import com.neqabty.presentation.ui.notifications.NotificationsViewModel
import com.neqabty.presentation.ui.payment.PaymentViewModel
import com.neqabty.presentation.ui.search.SearchViewModel
import com.neqabty.presentation.ui.oldsignup.SignupViewModel
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesViewModel
import com.neqabty.presentation.ui.syndicates.SyndicatesViewModel
import com.neqabty.presentation.ui.tripDetails.TripDetailsViewModel
import com.neqabty.presentation.ui.trips.TripsViewModel
import com.neqabty.presentation.ui.tripsReservation.TripReservationViewModel
import com.neqabty.presentation.ui.updateData.UpdateDataViewModel
import com.neqabty.presentation.ui.updateDataDetails.UpdateDataDetailsViewModel
import com.neqabty.presentation.ui.updateDataVerification.UpdateDataVerificationViewModel
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
    @ViewModelKey(LoginWithPasswordViewModel::class)
    abstract fun bindLoginWithPasswordViewModel(loginWithPasswordViewModel: LoginWithPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    abstract fun bindOldSignupViewModel(signupViewModel: SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ActivateAccountViewModel::class)
    abstract fun bindActivateAccountViewModel(activateAccountViewModel: ActivateAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(changePasswordViewModel: ChangePasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(com.neqabty.presentation.ui.signup.SignupViewModel::class)
    abstract fun bindSignupViewModel(signupViewModel: com.neqabty.presentation.ui.signup.SignupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalRenewViewModel::class)
    abstract fun bindMedicalRenewViewModel(medicalRenewViewModel: MedicalRenewViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalRenewDetailsViewModel::class)
    abstract fun bindMedicalRenewDetailsViewModel(medicalRenewDetailsViewModel: MedicalRenewDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalRenewUpdateViewModel::class)
    abstract fun bindMedicalRenewUpdateViewModel(medicalRenewUpdateViewModel: MedicalRenewUpdateViewModel): ViewModel

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
    @ViewModelKey(TripDetailsViewModel::class)
    abstract fun bindTripDetailsViewModel(tripDetailsViewModel: TripDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripReservationViewModel::class)
    abstract fun bindTripReservationViewModel(tripDetailsViewModel: TripReservationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationsViewModel::class)
    abstract fun bindNotificationsViewModel(notificationsViewModel: NotificationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotificationDetailsViewModel::class)
    abstract fun bindNotificationDetailsViewModel(notificationDetailsViewModel: NotificationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalProvidersViewModel::class)
    abstract fun bindMedicalProvidersViewModel(medicalProvidersViewModel: MedicalProvidersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChooseAreaViewModel::class)
    abstract fun bindChooseAreaViewModel(chooseAreaViewModel: ChooseAreaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalProfessionsViewModel::class)
    abstract fun bindMedicalProfessionsViewModel(medicalProfessionsViewModel: MedicalProfessionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MedicalProviderDetailsViewModel::class)
    abstract fun bindMedicalProviderDetailsViewModel(medicalProviderDetailsViewModel: MedicalProviderDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateDataViewModel::class)
    abstract fun bindUpdateDataViewModel(updateDataViewModel: UpdateDataViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateDataDetailsViewModel::class)
    abstract fun bindUpdateDataDetailsViewModel(updateDataDetailsViewModel: UpdateDataDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpdateDataVerificationViewModel::class)
    abstract fun bindUpdateDataVerificationViewModel(updateDataVerificationViewModel: UpdateDataVerificationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EngineeringRecordsInquiryViewModel::class)
    abstract fun bindEngineeringRecordsInquiryViewModel(
            engineeringRecordsInquiryViewModel: EngineeringRecordsInquiryViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EngineeringRecordsDetailsViewModel::class)
    abstract fun bindEngineeringRecordsDetailsViewModel(
            engineeringRecordsDetailsViewModel: EngineeringRecordsDetailsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InquiryViewModel::class)
    abstract fun bindInquiryViewModel(inquiryViewModel: InquiryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InquiryDetailsViewModel::class)
    abstract fun bindInquiryDetailsViewModel(inquiryDetailsViewModel: InquiryDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentViewModel::class)
    abstract fun bindPaymentViewModel(paymentViewModel: PaymentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComplaintViewModel::class)
    abstract fun bindComplaintViewModel(complaintViewModel: ComplaintViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoronaViewModel::class)
    abstract fun bindCoronaViewModel(coronaViewModel: CoronaViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: NeqabtyViewModelFactory): ViewModelProvider.Factory
}
