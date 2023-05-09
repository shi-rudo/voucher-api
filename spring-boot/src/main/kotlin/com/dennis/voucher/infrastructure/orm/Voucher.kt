package com.dennis.voucher.infrastructure.orm

import jakarta.persistence.*
import java.time.Instant
import java.util.*


// ACTIVE RECORD PATTERN AS DOMAIN ENTITY WOULD BE BETTER

@Entity
@Table(name = "vouchers")
class Voucher(

    @Id @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) val id: UUID = UUID.randomUUID(),

    @Column(name = "code") val code: String,

    @Column(name = "status") var status: String,

    @Column(name = "participant_email") var email: String,

    @Column(name = "participant_email_sent_at") var emailDate: Instant?

)
