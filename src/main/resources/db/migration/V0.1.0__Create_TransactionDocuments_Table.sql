CREATE TABLE app_transaction_documents (
    id UUID PRIMARY KEY,
    app_transaction_id UUID NOT NULL,
    name TEXT NOT NULL,
    path TEXT NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    created_by TEXT,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    updated_by TEXT
);

ALTER TABLE app_transaction_documents ADD CONSTRAINT UC_APP_TRANSACTION_DOCUMENTS_PATH
    UNIQUE (path);

ALTER TABLE app_transaction_documents ADD CONSTRAINT FK_APP_TRANSACTION_DOCUMENTS_ON_APP_TRANSACTION
    FOREIGN KEY (app_transaction_id)
    REFERENCES app_transactions (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE;
