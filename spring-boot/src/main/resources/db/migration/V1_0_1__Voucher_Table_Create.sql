create type voucher_code as enum ('AVAILABLE', 'PENDING', 'CONFIRMED');

create table vouchers
(
    id                        uuid default gen_random_uuid() not null
        constraint vouchers_pk
            primary key,
    code                      varchar                        not null,
    status                    varchar                        not null,
    participant_email         varchar,
    participant_email_sent_at timestamp with time zone
);

alter table vouchers
    owner to postgres;

create index vouchers_code_index
    on vouchers (code);

create index vouchers_status_index
    on vouchers (status);

create index vouchers_participant_email_index
    on vouchers (participant_email);