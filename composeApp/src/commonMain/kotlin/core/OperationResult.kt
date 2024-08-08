package core

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val exception: String) : OperationResult<Nothing>()
}