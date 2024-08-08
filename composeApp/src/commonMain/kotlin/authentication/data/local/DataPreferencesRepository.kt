package authentication.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataPreferencesRepository(private val dataPreferencesDao: DataPreferencesDao) {
    companion object {
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"
    }

    val isOnboardingCompleted: Flow<Boolean> = dataPreferencesDao.getValue(KEY_ONBOARDING_COMPLETED)
        .map { value -> value?.toBoolean() ?: false }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataPreferencesDao.insert(PreferenceEntity(KEY_ONBOARDING_COMPLETED, completed.toString()))
    }
}
