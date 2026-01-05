INSERT INTO medication (id, name, type, created_at) VALUES
('11111111-1111-1111-1111-111111111111', 'Novorapid (Aspart)', 'Insulin', NOW()),
('22222222-2222-2222-2222-222222222222', 'Humalog (Lispro)', 'Insulin', NOW()),
('33333333-3333-3333-3333-333333333333', 'Fiasp (Fast Aspart)', 'Insulin', NOW()),
('44444444-4444-4444-4444-444444444444', 'Lantus (Glargine)', 'Insulin', NOW()),
('55555555-5555-5555-5555-555555555555', 'Abasaglar (Glargine)', 'Insulin', NOW()),
('66666666-6666-6666-6666-666666666666', 'Toujeo', 'Insulin', NOW()),
('77777777-7777-7777-7777-777777777777', 'Tresiba (Degludec)', 'Insulin', NOW()),
('88888888-8888-8888-8888-888888888888', 'Mixtard 30', 'Insulin', NOW());


ALTER TABLE insulin_dose DROP COLUMN insulin_type;