ALTER TABLE patient
    ADD COLUMN physician_id UUID;

ALTER TABLE patient
    ADD CONSTRAINT fk_patient_physician FOREIGN KEY (physician_id) REFERENCES physician(id);

ALTER TABLE patient
    ADD COLUMN is_physician_confirmed BOOLEAN DEFAULT FALSE;