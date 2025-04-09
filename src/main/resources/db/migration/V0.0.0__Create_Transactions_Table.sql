CREATE TABLE app_transactions (
    id UUID PRIMARY KEY,
    currency_profile_id UUID NOT NULL,
    title VARCHAR(30) NOT NULL,
    description TEXT,
    transaction_type TEXT NOT NULL,
    amount NUMERIC(19,2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    category TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    created_by TEXT,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by TEXT
);
