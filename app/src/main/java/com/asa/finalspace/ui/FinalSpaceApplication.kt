package com.asa.finalspace.ui

import android.app.Application
import com.asa.finalspace.network.HttpClient.allCharactersService
import com.asa.finalspace.network.HttpClient.allEpisodesService
import com.asa.finalspace.network.HttpClient.allLocationsService
import com.asa.finalspace.repository.AllCharactersRepositoryImpl
import com.asa.finalspace.repository.AllLocationsRepositoryImpl
import com.asa.finalspace.repository.AllEpisodesRepositoryImpl
import com.asa.finalspace.viewmodel.AllCharactersViewModel
import com.asa.finalspace.viewmodel.AllEpisodesViewModel
import com.asa.finalspace.viewmodel.AllLocationsViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class FinalSpaceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
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
        }
    }
}