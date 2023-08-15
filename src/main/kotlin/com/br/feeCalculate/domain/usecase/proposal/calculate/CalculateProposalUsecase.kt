package com.br.feeCalculate.domain.usecase.proposal.calculate

import com.br.feeCalculate.commons.daysAt
import com.br.feeCalculate.commons.isWeekend
import com.br.feeCalculate.domain.entity.Check
import com.br.feeCalculate.domain.entity.Proposal
import com.br.feeCalculate.domain.usecase.Usecase
import java.math.BigDecimal
import java.math.RoundingMode.HALF_DOWN
import java.time.LocalDate
import java.time.LocalDate.now
import java.util.UUID.randomUUID
class CalculateProposalUsecase: Usecase<CreateProposalInput, Proposal> {

    override fun execute(input: CreateProposalInput): Proposal {

        val checks = input.checks.map {
            val awaitingDays: Long = calculateAwaitingDays(input.exchangeDate, it.dueDate,it.daysUntilCompensate)
            val fee: Float = calculateFee(input.fee, awaitingDays)
            val payedByChange: Float = calculateByChange(it.value, fee)
            val totalToReceiver: Float = calculateTotalToReceiver(it.value, payedByChange)

            val check = Check(
                id = randomUUID(),
                value = it.value,
                daysUntilCompensate = it.daysUntilCompensate,
                dueDate = now(),
                owner = it.owner,
                number = it.number,
                agency = it.agency,
                awaitingDays = awaitingDays,
                totalFee = fee,
                payedByChange = payedByChange,
                totalToReceiver = totalToReceiver,
            )
            check
        }

       return Proposal(
            id = randomUUID(),
            fee = input.fee,
            exchangeDate = input.exchangeDate,
            totalValue = setScale( checks.map { it.value }.sum()),
            totalPayedByChange = setScale( checks.map { it.payedByChange?: 0F }.sum()),
            totalToReceiver = setScale( checks.map { it.totalToReceiver ?: 0F }.sum()),
            mediaDays = checks.sumOf { it.awaitingDays ?: 0 }/checks.size,
            checks = checks
        )
    }

    private fun calculateAwaitingDays(exchangeDate: LocalDate, dueDate: LocalDate, daysUntilCompensate: Long): Long {
        val daysAt = exchangeDate.daysAt(dueDate) + daysUntilCompensate

        var daysUntilBusinessDay = 0L

        while (dueDate.plusDays(daysUntilBusinessDay).isWeekend()){
            daysUntilBusinessDay++
        }

        return daysAt + daysUntilBusinessDay
    }

    private fun calculateFee(fee: Float, days: Long): Float =
        setScale(fee * days.toFloat()/30)

    private fun calculateByChange(value: Float, totalFee: Float): Float  =
        setScale(value * totalFee/100)

    private fun calculateTotalToReceiver(value: Float, payedByChange: Float): Float =
        setScale(value - payedByChange)

    private fun setScale(input: Float): Float =
        BigDecimal(input.toDouble()).setScale(3, HALF_DOWN).toFloat()
}

