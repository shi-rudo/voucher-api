package com.dennis.voucher

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VoucherApplication

fun main(args: Array<String>) {
    runApplication<VoucherApplication>(*args)
}
