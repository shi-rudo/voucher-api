package com.dennis.voucher.infrastructure.http

import com.dennis.voucher.application.command.ConfirmVoucher
import com.dennis.voucher.application.command.ConfirmVoucherHandler
import com.dennis.voucher.application.command.ReserveVoucher
import com.dennis.voucher.application.command.ReserveVoucherHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class HttpController @Autowired constructor(
    val reserveVoucherHandler: ReserveVoucherHandler,
    val confirmVoucherHandler: ConfirmVoucherHandler
) {

    @PostMapping("\${API_PATH_RESERVE}")
    suspend fun reserveVoucher(@RequestBody request: ReserveVoucherRequest): ResponseEntity<ApiResponse> {
        return try {
            val command = ReserveVoucher(email = request.email)
            reserveVoucherHandler.handle(command)
            ResponseEntity.ok(ApiResponse.Success())
        } catch (e: Exception) {
            e.buildErrorResponse()
        }
    }

    // NOTE @ SPEC: SWITCHED TO GET FOR DOI CONFIRMATION LINK IN EMAIL
    @GetMapping("\${API_PATH_CONFIRM}")
    suspend fun confirmVoucher(
        @RequestParam token: String,
        @RequestParam email: String,
    ): ResponseEntity<ApiResponse> {
        return try {
            val command = ConfirmVoucher(email = email, code = token)
            confirmVoucherHandler.handle(command)
            ResponseEntity.ok(ApiResponse.Success())
        } catch (e: Exception) {
            e.buildErrorResponse()
        }
    }

    private fun Exception.buildErrorResponse(): ResponseEntity<ApiResponse> {
        return ResponseEntity.badRequest().body(ApiResponse.Failure(message = this.message ?: "unknown"))
    }
}

