package dev.katiebarnett.gamenightguru.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.gamenightguru.data.database.GameEntity
import dev.katiebarnett.gamenightguru.data.database.PlayEntity
import dev.katiebarnett.gamenightguru.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = GameDetailViewModel.Factory::class)
class GameDetailViewModel @AssistedInject constructor(
    @Assisted private val gameId: Long,
    private val repository: GameRepository
) : ViewModel() {

    private val _game = MutableStateFlow<GameEntity?>(null)
    val game: StateFlow<GameEntity?> = _game

    val plays: StateFlow<List<PlayEntity>> = repository.getPlaysForGame(gameId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            _game.value = repository.getGameById(gameId)
        }
    }

    fun addPlay(numPlayers: Int, playTime: Int, rating: Float) {
        viewModelScope.launch {
            repository.insertPlay(
                PlayEntity(
                    gameId = gameId,
                    numPlayers = numPlayers,
                    playTime = playTime,
                    rating = rating
                )
            )
        }
    }

    fun deletePlay(play: PlayEntity) {
        viewModelScope.launch {
            repository.deletePlay(play)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(gameId: Long): GameDetailViewModel
    }
}
