package com.dennis.voucher.infrastructure.http

sealed class ApiResponse {
    data class Success(val status: String = "success") : ApiResponse()
    data class Failure(val status: String = "error", val message: String) : ApiResponse()
}
