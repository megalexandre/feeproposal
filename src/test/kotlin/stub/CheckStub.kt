package stub

import com.br.feeCalculate.domain.entity.Check
import java.time.LocalDate
import java.util.UUID.randomUUID


val checkStub = Check(
    id = randomUUID(),
    value = 1000.0F,
    daysUntilCompensate = 0,
    dueDate = LocalDate.now(),
)