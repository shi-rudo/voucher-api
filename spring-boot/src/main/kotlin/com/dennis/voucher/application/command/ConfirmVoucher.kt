package com.dennis.voucher.application.command

data class ConfirmVoucher(
    val email: String,
    val code: String
)
