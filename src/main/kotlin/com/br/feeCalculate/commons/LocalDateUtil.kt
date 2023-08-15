package com.br.feeCalculate.commons

import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.SUNDAY
import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

fun LocalDate.isWeekend(): Boolean = this.dayOfWeek in listOf(SATURDAY, SUNDAY)

fun LocalDate.daysAt(other: LocalDate): Long =
    DAYS.between(this, other)