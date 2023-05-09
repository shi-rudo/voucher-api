package com.dennis.voucher.infrastructure.repository

import com.dennis.voucher.infrastructure.orm.Voucher
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.CrudRepository
import java.util.*


// TODO: IMPLEMENT ASYNC OR REACTIVE REPOSITORIES
interface VoucherRepository : CrudRepository<Voucher, UUID> {

    // PESSIMISTIC_WRITE: You get a lock on the record at the beginning of the transaction to write it.
    // You are saying "I am going to update this record, so no one can read or write until I am done". This means that
    // both attempts to perform a PESSIMISTIC_READ or PESSIMISTIC_WRITE will fail.

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findFirstByStatusIs(status: String): Voucher?

    fun findByEmail(email: String): Voucher?

}