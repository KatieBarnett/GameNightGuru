package dev.katiebarnett.gamenightguru.ui.plays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.katiebarnett.gamenightguru.data.database.PlayWithGame
import dev.katiebarnett.gamenightguru.data.repository.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayListViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    val plays: StateFlow<List<PlayWithGame>> = repository.getAllPlays()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}