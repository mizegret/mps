-- Site table
CREATE TABLE reference.site (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL CHECK (btrim(name) <> ''),
    url TEXT NOT NULL CHECK (btrim(url) <> ''),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);
COMMENT ON TABLE reference.site IS 'Sites for ...';
COMMENT ON COLUMN reference.site.id IS 'UUID primary key';
COMMENT ON COLUMN reference.site.name IS 'Display name (NOT blank)';
COMMENT ON COLUMN reference.site.url IS 'URL (NOT blank)';
COMMENT ON COLUMN reference.site.created_at IS 'Created at (UTC)';
COMMENT ON COLUMN reference.site.updated_at IS 'Updated at (UTC)';
