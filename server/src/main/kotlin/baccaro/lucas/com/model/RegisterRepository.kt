package baccaro.lucas.com.model

import auth.RegisterMemberResponse
import auth.RegisterOwnerResponse
import auth.UserRole
import baccaro.lucas.com.db.MemberPurposeRelations
import baccaro.lucas.com.db.Purposes
import baccaro.lucas.com.db.Users
import baccaro.lucas.com.plugins.generateInviteCode
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class RegisterRepository {

    fun registerUser(
        email: String,
        password: String,
        role: UserRole,
        inviteCode: String? = null
    ): Int {
        return transaction {
            Users.insertAndGetId {
                it[Users.email] = email
                it[Users.password] = password
                it[Users.role] = role
                it[Users.inviteCode] = inviteCode
            }.value
        }
    }

    fun registerOwner(
        email: String,
        password: String,
        purposeName: String,
        cbu: String
    ): RegisterOwnerResponse {
        return transaction {
            val inviteCode =
                generateInviteCode()
            val ownerId = registerUser(email, password, UserRole.OWNER, inviteCode)
            Purposes.insert {
                it[name] = purposeName
                it[Purposes.ownerId] = ownerId
                it[Purposes.inviteCode] = inviteCode
                it[Purposes.cbu] = cbu
            }
            RegisterOwnerResponse(ownerId, inviteCode)
        }
    }

    fun registerMember(
        email: String,
        password: String,
        inviteCode: String
    ): RegisterMemberResponse {
        val purpose = transaction {
            Purposes.select { Purposes.inviteCode eq inviteCode }.singleOrNull()
        } ?: throw IllegalArgumentException("Invalid invite code")

        val memberId = registerUser(email, password, UserRole.MEMBER, inviteCode)
        transaction {
            MemberPurposeRelations.insert {
                it[MemberPurposeRelations.memberId] = memberId
                it[MemberPurposeRelations.purposeId] = purpose[Purposes.id].value
            }
        }
        return RegisterMemberResponse(memberId)
    }

    fun getCbuByInviteCode(inviteCode: String): String? {
        return transaction {
            Purposes.select { Purposes.inviteCode eq inviteCode }
                .singleOrNull()
                ?.get(Purposes.cbu)
        }
    }
}
