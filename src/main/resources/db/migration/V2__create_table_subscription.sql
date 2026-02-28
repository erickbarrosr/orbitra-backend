CREATE TABLE subscription
(
    id                UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    plan_id           UUID        NOT NULL,
    status            VARCHAR(20) NOT NULL,
    start_date        DATE        NOT NULL,
    next_billing_date DATE        NOT NULL,
    created_at        TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_subscription_plan
        FOREIGN KEY (plan_id)
            REFERENCES plan (id)
            ON DELETE RESTRICT
);

CREATE INDEX idx_subscription_plan_id ON subscription (plan_id);
CREATE INDEX idx_subscription_status ON subscription (status);