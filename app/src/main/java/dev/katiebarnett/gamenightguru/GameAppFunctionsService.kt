package dev.katiebarnett.gamenightguru

//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.appfunctions.AppFunctionService
//import androidx.appfunctions.AppFunctionServiceEntryPoint
//import dagger.hilt.EntryPoint
//import dagger.hilt.InstallIn
//import dagger.hilt.android.AndroidEntryPoint
//import dagger.hilt.android.EntryPointAccessors
//import dagger.hilt.components.SingletonComponent
//import dev.katiebarnett.gamenightguru.data.repository.GameRepository
//
//@RequiresApi(Build.VERSION_CODES.BAKLAVA)
//@AndroidEntryPoint
//@AppFunctionServiceEntryPoint(
//    serviceName = "GameAppFunctionService",
//    appFunctionXmlFileName = "game_app_functions"
//)
//abstract class GameAppFunctions : AppFunctionService() {
//
//    private lateinit var gameRepository: GameRepository
//
//    override fun onCreate() {
//        super.onCreate()
//        val entryPoint = EntryPointAccessors.fromApplication(
//            applicationContext,
//            GameAppFunctionsEntryPoint::class.java
//        )
//        gameRepository = entryPoint.gameRepository()
//    }
//
//    @EntryPoint
//    @InstallIn(SingletonComponent::class)
//    interface GameAppFunctionsEntryPoint {
//        fun gameRepository(): GameRepository
//    }
//
//    // Add your App Functions here
//
//}
//
//// Add the GameSummary data class here
