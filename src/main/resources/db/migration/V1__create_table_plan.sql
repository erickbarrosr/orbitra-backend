CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE plan
(
    id         UUID PRIMARY KEY        DEFAULT uuid_generate_v4(),
    name       VARCHAR(100)   NOT NULL,
    price      NUMERIC(15, 2) NOT NULL CHECK (price >= 0),
    created_at TIMESTAMP      NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX idx_plan_name ON plan (name);