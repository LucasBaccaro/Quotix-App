package baccaro.lucas.com.db

import auth.UserRole
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Users : IntIdTable() {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val role = enumerationByName("role", 10, UserRole::class)
    val dni = varchar("dni", 20).nullable()
    val inviteCode = varchar("invite_code", 10).nullable()
}

object Purposes : IntIdTable() {
    val name = varchar("name", 255)
    val ownerId = reference("owner_id", Users)
    val inviteCode = varchar("invite_code", 10).uniqueIndex()
    val cbu = varchar("cbu", 22)
    val chargeAmount = decimal("charge_amount", 10, 2)
}

object Payments : IntIdTable() {
    val memberId = reference("member_id", Users)
    val purposeId = reference("purpose_id", Purposes)
    val amount = decimal("amount", 10, 2)
    val date = varchar("date", 100)
}

object MemberPurposeRelations : Table() {
    val memberId = reference("member_id", Users)
    val purposeId = reference("purpose_id", Purposes)
    override val primaryKey = PrimaryKey(memberId, purposeId, name = "PK_MemberPurposeRelations")
}