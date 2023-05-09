package com.dennis.voucher.infrastructure.repository

import com.dennis.voucher.infrastructure.orm.Voucher
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.CrudRepository
import java.util.*


// TODO: IMPLEMENT ASYNC OR REACTIVE REPOSITORIES
interface VoucherRepository : CrudRepository<Voucher, UUID> {

    // ensures that no other transaction can update the Voucher entity until the current transaction completes
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findFirstByStatusIs(status: String): Voucher?

    fun findByEmail(email: String): Voucher?


}