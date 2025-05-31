package com.asa.finalspace.di

import android.app.Application
import com.asa.finalspace.network.HttpClient.allCharactersService
import com.asa.finalspace.network.HttpClient.allEpisodesService
import com.asa.finalspace.network.HttpClient.allLocationsService
import com.asa.finalspace.repository.AllCharacterRepository
import com.asa.finalspace.repository.AllCharactersRepositoryImpl
import com.asa.finalspace.repository.AllEpisodesRepository
import com.asa.finalspace.repository.AllLocationsRepositoryImpl
import com.asa.finalspace.repository.AllEpisodesRepositoryImpl
import com.asa.finalspace.repository.AllLocationsRepository
import com.asa.finalspace.viewmodel.AllCharactersViewModel
import com.asa.finalspace.viewmodel.AllEpisodesViewModel
import com.asa.finalspace.viewmodel.AllLocationsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val apiModule = module {
    single { allCharactersService }
    single { allLocationsService }
    single { allEpisodesService }
}
val repositoryModule= module {
    single<AllCharacterRepository> { AllCharactersRepositoryImpl (get()) }
    single <AllLocationsRepository>{ AllLocationsRepositoryImpl (get()) }
    single < AllEpisodesRepository>{ AllEpisodesRepositoryImpl (get()) }
}
val viewModelModule = module {
    viewModelOf(::AllCharactersViewModel)
    viewModelOf(::AllLocationsViewModel)
    viewModelOf(::AllEpisodesViewModel)
}


class FinalSpaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FinalSpaceApplication)
            modules(
                apiModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}

/*
class FinalSpaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        */
/*startKoin {
            val appModule = module {
                single { allCharactersService }
                single { allLocationsService }
                single { allEpisodesService }

                single {
                    AllCharactersRepositoryImpl(allCharactersService = get())
                }
                single {
                    AllEpisodesRepositoryImpl(allEpisodesService = get())
                }
                single {
                    AllLocationsRepositoryImpl(allLocationService = get())
                }

                viewModel { (application: Application) ->
                    AllCharactersViewModel(
                        application = application,
                        allCharactersRepository = get()
                    )
                }
                viewModel { (application: Application) ->
                    AllEpisodesViewModel(
                        application = application,
                        allEpisodesRepository = get()
                    )
                }
                viewModel { (application: Application) ->
                    AllLocationsViewModel(
                        application = application,
                        allLocationsRepository = get()
                    )
                }
            }


//            modules(
//                module {
//
//                    singleOf( ::AllCharactersRepositoryImpl )
//
//                }
//            )
//            modules(
//                module{
//                    single { allLocationsService }
//                    singleOf( ::AllLocationsRepositoryImpl )
//                }
//            )
//            modules(
//                module{
//                    single { allEpisodesService }
//                    singleOf( ::AllEpisodesRepositoryImpl )
//                }
//            )
        }*//*

    }
}*/
