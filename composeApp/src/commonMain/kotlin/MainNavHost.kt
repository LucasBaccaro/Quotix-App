import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Onboarding

@Serializable
object Authentication

@Serializable
data class Home(val params: String)

@Serializable
object Loading