-- Add threshold fields to patient_clinical_setting table
ALTER TABLE patient_clinical_setting ADD COLUMN low_threshold INTEGER NOT NULL DEFAULT 70;
ALTER TABLE patient_clinical_setting ADD COLUMN critical_low_threshold INTEGER NOT NULL DEFAULT 54;
ALTER TABLE patient_clinical_setting ADD COLUMN high_threshold INTEGER NOT NULL DEFAULT 180;
ALTER TABLE patient_clinical_setting ADD COLUMN critical_high_threshold INTEGER NOT NULL DEFAULT 250;