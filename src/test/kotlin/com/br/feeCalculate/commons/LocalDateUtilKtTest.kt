package com.br.feeCalculate.commons

import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class LocalDateUtilKtTest{

    @Test
    fun `WHEN is sunday SHOULD return true`(){
        assertTrue(LocalDate.of(2023, 1, 1).isWeekend())
    }

    @Test
    fun `WHEN is saturn day SHOULD return true`(){
        assertTrue(LocalDate.of(2023, 8, 19).isWeekend())
    }

    @Test
    fun `WHEN is not weekend SHOULD return false`(){
        repeat(5){ i ->
            assertFalse(LocalDate.of(2023, 8, 7 + i).isWeekend())
        }
    }

    @Test
    fun `WHEN calculate diff between one day SHOULD 1`(){
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        assertEquals(1L,today.daysAt(tomorrow))
    }

    @Test
    fun `WHEN calculate all days os september SHOULD return 29`(){
        val today = LocalDate.of(2023,9,1)
        val tomorrow = LocalDate.of(2023,9,30)

        assertEquals(29L,today.daysAt(tomorrow))
    }

}