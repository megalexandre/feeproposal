package com.br.feeCalculate.domain.entity

import com.br.feeCalculate.infrastructure.InvalidCreationConditionException
import java.time.LocalDate
import java.util.UUID

data class Check(
    val id: UUID,

    val value: Float,
    val daysUntilCompensate: Long,
    val dueDate: LocalDate,

    val owner: String? = null,
    val number: String? = null,
    val agency: String? = null,

    val awaitingDays: Long? = null,
    val totalFee: Float? = null,
    val payedByChange: Float? = null,
    val totalToReceiver: Float? = null,
){

    init {

        if(value <= 0){
            throw InvalidCreationConditionException("error003")
        }

        if(daysUntilCompensate < 0){
            throw InvalidCreationConditionException("error001")
        }

    }

}
