package com.br.feeCalculate.domain.usecase.proposal.calculate

import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import stub.checkInputStub
import stub.createProposalInputStub

internal class CalculateProposalUsecaseTest{

    @Test
    fun `WHEN create a proposal SHOULD create id`(){
        val response = CalculateProposalUsecase().execute(createProposalInputStub)

        assertNotNull(response.id)
        assertNotNull(response.checks.first().id)
        assertThat(response.checks.size).isEqualTo(1)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate days`(){
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = LocalDate.of(2023, 9,30)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            exchangeDate = exchangeDate,
            checks = listOf(checkInputStub.copy(
                dueDate = dueDate,
                daysUntilCompensate = daysUntilCompensate,
            ))
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(33, response.checks.first().awaitingDays)
    }

    @Test
    fun `WHEN create a proposal at weed end SHOULD calculate days`(){
        val exchangeDate =  LocalDate.of(2023, 9,1)
        val dueDate = exchangeDate.plusDays(45)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            exchangeDate = exchangeDate,
            checks = listOf(checkInputStub.copy(
                dueDate = dueDate,
                daysUntilCompensate = daysUntilCompensate,
            ))
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(47, response.checks.first().awaitingDays)
    }


    @Test
    fun `WHEN create a proposal SHOULD calculate total fee`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = LocalDate.of(2023, 9,30)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(checkInputStub.copy(
                dueDate = dueDate,
                value = value,
                daysUntilCompensate = daysUntilCompensate,
            ))
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(5.50F, response.checks.first().totalFee)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate payed value`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = LocalDate.of(2023, 9,30)
        val secondDueDate = LocalDate.of(2023, 10,16)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
                checkInputStub.copy(
                    dueDate = secondDueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                )
            )
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(55.00F, response.checks[0].payedByChange)
        assertEquals(78.33F, response.checks[1].payedByChange)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate total to receiver`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = LocalDate.of(2023, 9,30)
        val secondDueDate = LocalDate.of(2023, 10,16)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
                checkInputStub.copy(
                    dueDate = secondDueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                )
            )
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(1866.67F, response.totalToReceiver)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate proposal total to receiver`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = exchangeDate.plusDays(30)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
            )
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(2000.0F, response.totalValue)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate proposal media days`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)
        val dueDate = exchangeDate.plusDays(30)
        val secondDueDate = exchangeDate.plusDays(45)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
                checkInputStub.copy(
                    dueDate = secondDueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
            )
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(40,response.mediaDays)
    }

    @Test
    fun `WHEN create a proposal SHOULD calculate proposal totalPayedByChange`(){
        val value = 1000F
        val fee = 5F
        val exchangeDate = LocalDate.of(2023, 9,1)

        val dueDate = LocalDate.of(2023, 9,30)
        val secondDueDate = LocalDate.of(2023, 10,16)
        val daysUntilCompensate = 2L

        val proposal = createProposalInputStub.copy(
            fee = fee,
            exchangeDate = exchangeDate,
            checks = listOf(
                checkInputStub.copy(
                    dueDate = dueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
                checkInputStub.copy(
                    dueDate = secondDueDate,
                    value = value,
                    daysUntilCompensate = daysUntilCompensate,
                ),
            )
        )

        val response = CalculateProposalUsecase().execute(proposal)
        assertEquals(133.33F, response.totalPayedByChange)
    }

}