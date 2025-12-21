UPDATE patient SET id = user_id WHERE user_id IS NOT NULL;

ALTER TABLE patient DROP CONSTRAINT IF EXISTS patient_id_fkey;
ALTER TABLE patient ADD CONSTRAINT patient_id_fkey FOREIGN KEY (id) REFERENCES "user"(id) ON DELETE CASCADE;

ALTER TABLE patient DROP COLUMN user_id;

UPDATE physician SET id = user_id WHERE user_id IS NOT NULL;
ALTER TABLE physician DROP CONSTRAINT IF EXISTS physician_id_fkey;
ALTER TABLE physician ADD CONSTRAINT physician_id_fkey FOREIGN KEY (id) REFERENCES "user"(id) ON DELETE CASCADE;
ALTER TABLE physician DROP COLUMN user_id;