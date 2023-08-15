package com.br.feeCalculate.domain.entity

import com.br.feeCalculate.infrastructure.InvalidCreationConditionException
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDate.now
import java.util.UUID
import java.util.UUID.randomUUID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import stub.checkStub

internal class ProposalTest{

    @Test
    fun `WHEN all arguments is valid SHOULD create them`(){
        assertDoesNotThrow {
            Proposal(
                id = randomUUID(),
                fee = 1F,
                exchangeDate = now(),
                checks = listOf(checkStub),
                totalValue = 1000F,
                totalPayedByChange = 945F,
                totalToReceiver = 550F,
                mediaDays = 33,
            )
        }
    }

    @Test
    fun `WHEN fee is equal zero SHOULD throw`(){
        assertThrows<InvalidCreationConditionException> {
            Proposal(
                id = randomUUID(),
                fee = 0F,
                exchangeDate = now(),
                checks = listOf(checkStub),
                totalValue = 1000F,
                totalPayedByChange = 945F,
                totalToReceiver = 550F,
                mediaDays = 33
            )
        }
    }

    @Test
    fun `WHEN proposal has any check with due date before exchange date SHOULD throw`(){
        assertThrows<InvalidCreationConditionException> {
            Proposal(
                id = randomUUID(),
                fee = 0F,
                exchangeDate = now(),
                checks = listOf(checkStub.copy(dueDate = now().minusDays(1))),
                totalValue = 1000F,
                totalPayedByChange = 945F,
                totalToReceiver = 550F,
                mediaDays = 33
            )
        }
    }

    @Test
    fun `WHEN proposal has empty check list SHOULD throw`(){
        assertThrows<InvalidCreationConditionException> {
            Proposal(
                id = randomUUID(),
                fee = 0F,
                exchangeDate = now(),
                checks = listOf(),
                totalValue = 1000F,
                totalPayedByChange = 945F,
                totalToReceiver = 550F,
                mediaDays = 33
            )
        }
    }
}