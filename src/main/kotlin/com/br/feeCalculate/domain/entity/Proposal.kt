package com.br.feeCalculate.domain.entity

import com.br.feeCalculate.infrastructure.InvalidCreationConditionException
import java.time.LocalDate
import java.util.UUID

data class Proposal(
    val id: UUID,
    val fee: Float,
    val exchangeDate: LocalDate,
    val checks: List<Check>,
    val considerFirstWorkingDay: Boolean = true,
    val totalValue: Float,
    val totalPayedByChange: Float,
    val totalToReceiver: Float,
    val mediaDays: Long,
) {

    init {

        if(checks.isEmpty()){
            throw InvalidCreationConditionException("error004")
        }

        if(fee <= 0){
            throw InvalidCreationConditionException("error005")
        }

        if(checks.any { it.dueDate.isBefore(exchangeDate) }){
            throw InvalidCreationConditionException("error005")
        }
    }
}