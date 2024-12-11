package com.example.it_tech_hack.features.briefcase.models

import com.example.hz.common.BaseState
import com.example.it_tech_hack.domain.models.InvestmentType

data class BriefcaseState(
    val percentageData: List<Pair<InvestmentType, Double>> = emptyList(),
    val priceData: List<Pair<String, Double>> = emptyList() // Используем InvestmentType вместо String
) : BaseState
