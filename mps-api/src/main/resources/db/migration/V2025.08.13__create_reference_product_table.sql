-- Create schema (idempotent)
CREATE SCHEMA IF NOT EXISTS reference;
COMMENT ON SCHEMA reference IS 'Reference/master data';
-- Product table
CREATE TABLE reference.product (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL CHECK (btrim(name) <> ''),
    description TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE reference.product IS 'Products for …';
COMMENT ON COLUMN reference.product.id IS 'UUID primary key';
COMMENT ON COLUMN reference.product.name IS 'Display name (NOT blank)';
COMMENT ON COLUMN reference.product.description IS 'Optional detail';
COMMENT ON COLUMN reference.product.created_at IS 'Created at (UTC)';
COMMENT ON COLUMN reference.product.updated_at IS 'Updated at (UTC)';