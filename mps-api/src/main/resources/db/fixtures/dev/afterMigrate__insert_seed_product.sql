CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- 20 product for dev dummy（idempotent）
INSERT INTO reference.product (id, name, description, created_at, updated_at)
VALUES (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 01'),
        'Sample Product 01',
        'Dev seed item 01',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 02'),
        'Sample Product 02',
        'Dev seed item 02',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 03'),
        'Sample Product 03',
        'Dev seed item 03',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 04'),
        'Sample Product 04',
        'Dev seed item 04',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 05'),
        'Sample Product 05',
        'Dev seed item 05',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 06'),
        'Sample Product 06',
        'Dev seed item 06',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 07'),
        'Sample Product 07',
        'Dev seed item 07',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 08'),
        'Sample Product 08',
        'Dev seed item 08',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 09'),
        'Sample Product 09',
        'Dev seed item 09',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 10'),
        'Sample Product 10',
        'Dev seed item 10',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 11'),
        'Sample Product 11',
        'Dev seed item 11',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 12'),
        'Sample Product 12',
        'Dev seed item 12',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 13'),
        'Sample Product 13',
        'Dev seed item 13',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 14'),
        'Sample Product 14',
        'Dev seed item 14',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 15'),
        'Sample Product 15',
        'Dev seed item 15',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 16'),
        'Sample Product 16',
        'Dev seed item 16',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 17'),
        'Sample Product 17',
        'Dev seed item 17',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 18'),
        'Sample Product 18',
        'Dev seed item 18',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 19'),
        'Sample Product 19',
        'Dev seed item 19',
        NOW(),
        NOW()
    ),
    (
        uuid_generate_v5(uuid_ns_url(), 'product:Sample Product 20'),
        'Sample Product 20',
        'Dev seed item 20',
        NOW(),
        NOW()
    ) ON CONFLICT (id) DO
UPDATE
SET name = EXCLUDED.name,
    description = EXCLUDED.description,
    updated_at = NOW();