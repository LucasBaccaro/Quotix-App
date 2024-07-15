package baccaro.lucas.com.plugins

fun generateInviteCode(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    return (1..10)
        .map { chars.random() }
        .joinToString("")
}