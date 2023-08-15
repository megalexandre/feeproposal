package com.br.feeCalculate.domain.entity

import com.br.feeCalculate.infrastructure.InvalidCreationConditionException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import stub.checkStub

internal class CheckTest{

    @Test
    fun `WHEN value is zero SHOULD throw exception`(){

        assertThrows<InvalidCreationConditionException> {
            checkStub.copy(
                value = 0F
            )
        }
    }

    @Test
    fun `WHEN value is less then zero SHOULD throw exception`(){

        assertThrows<InvalidCreationConditionException> {
            checkStub.copy(
                value = -1F
            )
        }
    }

    @Test
    fun `WHEN daysUntilCompensate is less then zero SHOULD throw exception`(){

        assertThrows<InvalidCreationConditionException> {
            checkStub.copy(
                daysUntilCompensate = -1
            )
        }
    }
}