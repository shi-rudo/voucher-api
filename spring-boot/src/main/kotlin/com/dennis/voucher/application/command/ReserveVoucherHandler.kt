package com.dennis.voucher.application.command

import com.dennis.voucher.application.service.EmailService
import com.dennis.voucher.application.service.VoucherService
import com.dennis.voucher.infrastructure.orm.Voucher
import org.springframework.stereotype.Service

@Service
class ReserveVoucherHandler(
    private val voucherService: VoucherService,
    private val emailService: EmailService
) {

    /**
     * Reserves a voucher for the given email to PENDING status and sends an email to user with the voucher code
     * and a link that must be clicked to confirm the voucher.
     */
    fun handle(command: ReserveVoucher) {
        command.guardExistingEmail()
            .retrieveVoucher()
            .sendEmail()
    }

    /**
     * Runs the validation guards for the command defined by the spec
     */
    fun ReserveVoucher.guardExistingEmail(): ReserveVoucher {
        val result = voucherService
            .checkIfEmailRegistered(this.email)
        if (result) throw Exception("email_already_registered")
        return this
    }

    fun ReserveVoucher.retrieveVoucher(): Voucher {
        return voucherService
            .reserveAndSaveVoucher(this.email)
            ?: throw Exception("no_vouchers")
    }

    fun Voucher.sendEmail() {
        emailService.sendVoucherEmail(email, this)
    }
}