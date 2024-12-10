package com.example.it_tech_hack.features.mainScreen

import com.example.hz.common.BaseViewModel
import com.example.it_tech_hack.features.mainScreen.model.MainAction
import com.example.it_tech_hack.features.mainScreen.model.MainIntent
import com.example.it_tech_hack.features.mainScreen.model.MainState

class MainScreenViewModel(

): BaseViewModel<MainState, MainAction, MainIntent>() {

    init {

    }


    override fun createInitialState(): MainState =
        MainState()

    override fun handleIntent(intent: MainIntent) {

    }

    private fun getCostOfBriefCase(){

    }

}