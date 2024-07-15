package baccaro.lucas.com.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/quotix",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "lbaccaro1"
        )
        transaction {
            SchemaUtils.create(Users, Purposes, Payments, MemberPurposeRelations)
        }
    }
}