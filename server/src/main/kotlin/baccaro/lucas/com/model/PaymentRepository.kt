package baccaro.lucas.com.model

import auth.Payment
import auth.PaymentRequest
import baccaro.lucas.com.db.Payments
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentRepository {

    fun addPayment(paymentRequest: PaymentRequest): Payment {
        return transaction {
            val paymentId = Payments.insertAndGetId {
                it[memberId] = paymentRequest.memberId
                it[purposeId] = paymentRequest.purposeId
                it[amount] = paymentRequest.amount.toBigDecimal()
                it[date] = paymentRequest.date
            }.value

            Payment(
                id = paymentId,
                memberId = paymentRequest.memberId,
                purposeId = paymentRequest.purposeId,
                amount = paymentRequest.amount,
                date = paymentRequest.date
            )
        }
    }
}