package com.neqabty.presentation.di

import android.app.Application
import com.neqabty.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

// responsible for injecting the Application class
@Singleton // Dagger doesn’t allow for unscoped components to refer to scoped bindings
@Component(
    modules = [
        AndroidInjectionModule::class, // needed to ensure the binding of the Android base types (Activities, Fragments, etc.)
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent { // extends AndroidInjector<MyApplication>
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
    // allow to inject into our App
    fun inject(myApp: MyApp)
}

// ////region///////////////////
// package com.neqabty.presentation.di
//
// import android.app.Application
// import com.neqabty.MyApp
// import dagger.BindsInstance
// import dagger.Component
// import dagger.android.AndroidInjectionModule
// import javax.inject.Singleton
//
// //responsible for injecting the Application class
// @Singleton//Dagger doesn’t allow for unscoped components to refer to scoped bindings
// @Component(
//        modules = [
//            AndroidInjectionModule::class, // needed to ensure the binding of the Android base types (Activities, Fragments, etc.)
//            AppModule::class,
//            NetworkModule::class,
//            DataModule::class,
//            MainActivityModule::class]
// )
// interface AppComponent { // extends AndroidInjector<MyApplication>
//    @Component.Builder
//    interface Builder {
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun networkModule(networkModule: NetworkModule): Builder
//
//        fun dataModule(dataModule: DataModule): Builder
//
//        fun build(): AppComponent
//    }
//    // allow to inject into our App
//    fun inject(myApp: MyApp)
// }

//endregion/////////////////