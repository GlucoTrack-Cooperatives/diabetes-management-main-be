CREATE INDEX IF NOT EXISTS idx_glucose_reading_patient_timestamp
    ON glucose_reading (patient_id, "timestamp" DESC);