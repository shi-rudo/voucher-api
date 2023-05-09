package com.dennis.voucher.application.service

import com.dennis.voucher.infrastructure.orm.Voucher
import com.dennis.voucher.infrastructure.repository.EmailConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Component

@Component
class EmailService @Autowired constructor(
    val mailSender: EmailConfig
) {

    @Value("\${BACKEND_HOST_PRIMARY}")
    private val host: String? = null


    @Value("\${SPRING_LOCAL_PORT}")
    private val port: String? = null

    @Value("\${SPRING_HTTP_PROTOCOL}")
    private val protocol: String? = null


    fun sendVoucherEmail(to: String, voucher: Voucher) {
        val sender = mailSender.javaMailSender()
        val message = SimpleMailMessage()
        message.setTo(to)
        message.from = "server@dlds.de"
        message.subject = "Voucher Code"
        message.text = voucher.buildText()
        message.subject = "Voucher Code"
        sender.runCatching { send(message) }.onFailure { println(it.message) }
    }

    private fun Voucher.buildText(): String {
        val url = "$protocol://$host:$port/api/participants/confirm"
        val params = "token=$code&email=$email"
        return "Your voucher code is ${this.code}. You need to confirm by clicking this link: $url?$params"
    }
}

