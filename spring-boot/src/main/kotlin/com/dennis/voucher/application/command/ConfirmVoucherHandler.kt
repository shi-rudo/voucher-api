package com.dennis.voucher.application.command

import com.dennis.voucher.application.service.VoucherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfirmVoucherHandler @Autowired constructor(
    val voucherService: VoucherService
) {

    /**
     * Sets the status of the voucher to confirmed
     */
    fun handle(command: ConfirmVoucher) {
        command.runGuards()
            .confirm()
    }

    /**
     * Runs the validation guards for the command defined by the spec
     */
    private fun ConfirmVoucher.runGuards(): ConfirmVoucher {
        this
            .guardEmail()
            .guardAlreadyConfirmed()
            .guardEmailNotSent()
            .guardTokenInvalid()
        return this
    }

    private fun ConfirmVoucher.confirm() {
        voucherService.confirmVoucherByEmail(this.email)
    }

    private fun ConfirmVoucher.guardAlreadyConfirmed(): ConfirmVoucher {
        val result = voucherService.checkIfVoucherIsConfirmedByEmail(this.email)
        if (result) throw Exception("already_confirmed")
        return this
    }

    private fun ConfirmVoucher.guardEmail(): ConfirmVoucher {
        voucherService.findVoucherByEmail(this.email)
            ?: throw Exception("email_not_registered")
        return this
    }

    private fun ConfirmVoucher.guardEmailNotSent(): ConfirmVoucher {
        voucherService.findVoucherByEmail(this.email)
            ?.let {
                if ((it.status == "PENDING") && (it.emailDate == null)) {
                    throw Exception("not_confirmable")
                }
            }
        return this
    }

    private fun ConfirmVoucher.guardTokenInvalid(): ConfirmVoucher {
        voucherService.findVoucherByEmail(this.email)
            ?.let {
                if (it.code != this.code) {
                    throw Exception("invalid")
                }
            }
        return this
    }
}