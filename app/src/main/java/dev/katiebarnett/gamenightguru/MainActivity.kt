package dev.katiebarnett.gamenightguru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import dev.katiebarnett.gamenightguru.ui.detail.GameDetailScreen
import dev.katiebarnett.gamenightguru.ui.list.GameListScreen
import dev.katiebarnett.gamenightguru.ui.theme.GameNightGuruTheme

data object GameListRoute
data class GameDetailRoute(val gameId: Long)
data object PlayListRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameNightGuruTheme {
                val backStack = remember { 
                    val initialRoute = mutableStateListOf<Any>(GameListRoute)
                    if (intent.hasExtra("gameId")) {
                        initialRoute.add(GameDetailRoute(intent.getLongExtra("gameId", 0L)))
                    }
                    initialRoute
                }
                
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = entryProvider {
                        entry<GameListRoute> {
                            GameListScreen(
                                onGameClick = { gameId ->
                                    backStack.add(GameDetailRoute(gameId))
                                },
                                onPlayListClick = {
                                    backStack.add(PlayListRoute)
                                }
                            )
                        }
                        entry<GameDetailRoute> { route ->
                            GameDetailScreen(
                                gameId = route.gameId, 
                                onBack = { backStack.removeLastOrNull() }
                            )
                        }
                        entry<PlayListRoute> {
                            dev.katiebarnett.gamenightguru.ui.plays.PlayListScreen(
                                onBack = { backStack.removeLastOrNull() }
                            )
                        }
                    }
                )
            }
        }
    }
}
