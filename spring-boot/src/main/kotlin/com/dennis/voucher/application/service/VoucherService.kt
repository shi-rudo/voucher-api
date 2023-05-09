package com.dennis.voucher.application.service

import com.dennis.voucher.infrastructure.orm.Voucher
import com.dennis.voucher.infrastructure.repository.VoucherRepository
import jakarta.persistence.LockModeType
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class VoucherService @Autowired constructor(
    private val repository: VoucherRepository
) {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    fun confirmVoucherByEmail(email: String) {
        val voucher = repository.findByEmail(email)
        if (voucher != null) {
            voucher.status = "CONFIRMED"
            repository.save(voucher)
        } else {
            throw Exception("invalid")
        }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    fun reserveAndSaveVoucher(email: String): Voucher? {
        val voucher = repository.findFirstByStatusIs("AVAILABLE") ?: return null // No free vouchers left
        voucher.status = "PENDING"
        voucher.email = email
        voucher.emailDate = Instant.now()
        return repository.save(voucher)
    }

    fun checkIfEmailRegistered(email: String): Boolean = repository.findByEmail(email) != null

    fun checkIfVoucherIsConfirmedByEmail(email: String): Boolean = repository.findByEmail(email)?.status == "CONFIRMED"

    fun findVoucherByEmail(email: String): Voucher? = repository.findByEmail(email)
}