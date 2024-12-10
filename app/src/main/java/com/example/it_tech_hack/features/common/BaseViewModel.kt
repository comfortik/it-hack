package com.example.hz.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<State: BaseState, Action: BaseAction, Intent: BaseIntent>: ViewModel() {
    protected val _state by lazy{
        MutableStateFlow(createInitialState())
    }
    protected val state: State get()= _state.value
    val screenState: StateFlow<State> = _state.asStateFlow()

    protected val _action = MutableSharedFlow<Action>(replay = 1)
    val action: SharedFlow<Action> = _action.asSharedFlow()

    protected val _intent = MutableSharedFlow<Intent>()
    protected val intent: SharedFlow<Intent>  get()= _intent.asSharedFlow()
    val screenIntent = _intent.asSharedFlow()

    init {
        observeIntent()
    }


    abstract fun createInitialState():State

    abstract fun handleIntent(intent: Intent)

    private fun observeIntent(){
        viewModelScope.launch {
            intent.collect{handleIntent(it)}
        }
    }

    protected fun sendAction(action: Action){
        viewModelScope.launch {
            _action.emit(action)
        }
    }

    fun processIntent(intent: Intent){
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }



}