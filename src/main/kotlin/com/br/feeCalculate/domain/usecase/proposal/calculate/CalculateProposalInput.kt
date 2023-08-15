package com.br.feeCalculate.domain.usecase.proposal.calculate

import java.time.LocalDate

data class CreateProposalInput (
    val fee: Float,
    val exchangeDate: LocalDate,
    val checks: List<CheckInput>
)

data class CheckInput(
    val value: Float,
    val daysUntilCompensate: Long,
    val dueDate: LocalDate,

    val owner: String? = null,
    val number: String? = null,
    val agency: String? = null,
)