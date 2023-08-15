package stub

import com.br.feeCalculate.domain.usecase.proposal.calculate.CheckInput
import com.br.feeCalculate.domain.usecase.proposal.calculate.CreateProposalInput
import java.time.LocalDate.now

val createProposalInputStub = CreateProposalInput(
    fee = 5.0F,
    exchangeDate = now(),
    checks = listOf(
        CheckInput(
            value = 1000.00F,
            daysUntilCompensate = 2,
            dueDate = now().plusDays(30),
            owner = null,
            number = null,
            agency = null,
        )
    )
)

val checkInputStub: CheckInput = CheckInput(
    value = 1000.0F,
    daysUntilCompensate = 2,
    dueDate = now().plusDays(30),
    owner = null,
    number = null,
    agency = null,
)

val checksInputStub: List<CheckInput> = listOf(checkInputStub)