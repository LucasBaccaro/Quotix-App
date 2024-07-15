package baccaro.lucas.com.model

import auth.Payment
import auth.User
import baccaro.lucas.com.db.MemberPurposeRelations
import baccaro.lucas.com.db.Payments
import baccaro.lucas.com.db.Users
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun getUsersByPurpose(purposeId: Int): List<User> {
        return transaction {
            // Realizar una sola consulta que una las tablas Users y Payments
            val query = (Users innerJoin MemberPurposeRelations leftJoin Payments)
                .slice(
                    Users.id, Users.email, Users.role, Users.inviteCode,
                    Payments.id, Payments.amount, Payments.date, Payments.memberId
                )
                .select { MemberPurposeRelations.purposeId eq purposeId }
                .map {
                    // Crear un mapa para agrupar los pagos por usuario
                    val userId = it[Users.id].value
                    val payment = it[Payments.id]?.let { paymentId ->
                        Payment(
                            id = paymentId.value,
                            memberId = userId,
                            purposeId = purposeId,
                            amount = it[Payments.amount].toDouble(),
                            date = it[Payments.date].toString()
                        )
                    }

                    userId to payment
                }

            // Agrupar los pagos por usuario
            val groupedPayments =
                query.groupBy({ it.first }, { it.second }).mapValues { it.value.filterNotNull() }

            // Mapear usuarios con sus pagos
            groupedPayments.map { (userId, payments) ->
                val userRow = Users.select { Users.id eq userId }.single()
                User(
                    email = userRow[Users.email],
                    role = userRow[Users.role],
                    payments = payments
                )
            }
        }
    }
}