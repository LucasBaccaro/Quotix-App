package baccaro.lucas.com.model

import auth.User
import baccaro.lucas.com.db.Users
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class LoginRepository {
    fun loginUser(email: String, password: String): User? {
        return transaction {
            Users.select { (Users.email eq email) and (Users.password eq password) }
                .mapNotNull { row ->
                    User(
                        id = row[Users.id].value,
                        email = row[Users.email],
                        inviteCode = row[Users.inviteCode]
                    )
                }.singleOrNull()
        }
    }
}