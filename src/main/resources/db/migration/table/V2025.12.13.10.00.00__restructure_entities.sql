-- Restructure user, patient, and physician tables
-- ALTER TABLE "user" ADD COLUMN full_name VARCHAR(255); -- This is already done in a previous migration
-- ALTER TABLE "user" RENAME COLUMN password_hash TO password; -- This is also done in a previous migration

ALTER TABLE patient ADD COLUMN user_id UUID;
ALTER TABLE patient ADD CONSTRAINT fk_patient_user FOREIGN KEY (user_id) REFERENCES "user"(id);
ALTER TABLE patient DROP COLUMN first_name;
ALTER TABLE patient DROP COLUMN last_name;

ALTER TABLE physician ADD COLUMN user_id UUID;
ALTER TABLE physician ADD CONSTRAINT fk_physician_user FOREIGN KEY (user_id) REFERENCES "user"(id);
ALTER TABLE physician DROP COLUMN first_name;
ALTER TABLE physician DROP COLUMN last_name;

-- Create new tables
CREATE TABLE sleep_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL,
    duration_minutes INTEGER NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_sleep_log_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE TABLE water_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    patient_id UUID NOT NULL,
    glasses_count INTEGER NOT NULL,
    log_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT fk_water_log_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

-- Alter existing tables
ALTER TABLE food_log ADD COLUMN patient_id UUID;
ALTER TABLE food_log ADD CONSTRAINT fk_food_log_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE food_log RENAME COLUMN carbs_grams TO carbs;
ALTER TABLE food_log DROP COLUMN food_name;
ALTER TABLE food_log DROP COLUMN input_method;
ALTER TABLE food_log ADD COLUMN description TEXT;

ALTER TABLE insulin_dose ADD COLUMN patient_id UUID;
ALTER TABLE insulin_dose ADD CONSTRAINT fk_insulin_dose_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE insulin_dose ADD COLUMN medication_id UUID;
ALTER TABLE insulin_dose ADD CONSTRAINT fk_insulin_dose_medication FOREIGN KEY (medication_id) REFERENCES medication(id);
ALTER TABLE insulin_dose ALTER COLUMN units TYPE DOUBLE PRECISION;

ALTER TABLE activity_log ADD COLUMN patient_id UUID;
ALTER TABLE activity_log ADD CONSTRAINT fk_activity_log_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE activity_log RENAME COLUMN step_count TO steps;
ALTER TABLE activity_log ADD COLUMN "timestamp" TIMESTAMP;
ALTER TABLE activity_log DROP COLUMN date;
ALTER TABLE activity_log DROP COLUMN active_energy_burned;
ALTER TABLE activity_log DROP COLUMN exercise_minutes;
ALTER TABLE activity_log DROP COLUMN sleep_duration_minutes;
ALTER TABLE activity_log DROP COLUMN sleep_start_time;
ALTER TABLE activity_log DROP COLUMN sleep_end_time;

ALTER TABLE biometrics_log ADD COLUMN patient_id UUID;
ALTER TABLE biometrics_log ADD CONSTRAINT fk_biometrics_log_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE biometrics_log DROP COLUMN water_intake_ml;

ALTER TABLE health_event ADD COLUMN patient_id UUID;
ALTER TABLE health_event ADD CONSTRAINT fk_health_event_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE health_event RENAME COLUMN category TO event_type;
ALTER TABLE health_event DROP COLUMN severity;
ALTER TABLE health_event DROP COLUMN note;

ALTER TABLE glucose_reading ADD COLUMN patient_id UUID;
ALTER TABLE glucose_reading ADD CONSTRAINT fk_glucose_reading_patient FOREIGN KEY (patient_id) REFERENCES patient(id);

ALTER TABLE daily_patient_stat ADD COLUMN patient_id UUID;
ALTER TABLE daily_patient_stat ADD CONSTRAINT fk_daily_patient_stat_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE daily_patient_stat RENAME COLUMN time_in_range_percent TO time_in_range;
ALTER TABLE daily_patient_stat RENAME COLUMN time_below_range_percent TO time_below_range;

ALTER TABLE chat_thread ADD COLUMN patient_id UUID;
ALTER TABLE chat_thread ADD CONSTRAINT fk_chat_thread_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE chat_thread ADD COLUMN physician_id UUID;
ALTER TABLE chat_thread ADD CONSTRAINT fk_chat_thread_physician FOREIGN KEY (physician_id) REFERENCES physician(id);

ALTER TABLE chat_message ADD COLUMN chat_thread_id UUID;
ALTER TABLE chat_message ADD CONSTRAINT fk_chat_message_chat_thread FOREIGN KEY (chat_thread_id) REFERENCES chat_thread(id);
ALTER TABLE chat_message ADD COLUMN sender_id UUID;
ALTER TABLE chat_message ADD CONSTRAINT fk_chat_message_sender FOREIGN KEY (sender_id) REFERENCES "user"(id);

ALTER TABLE alert ADD COLUMN patient_id UUID;
ALTER TABLE alert ADD CONSTRAINT fk_alert_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE alert RENAME COLUMN type TO severity;

ALTER TABLE patient_clinical_setting ADD COLUMN patient_id UUID;
ALTER TABLE patient_clinical_setting ADD CONSTRAINT fk_patient_clinical_setting_patient FOREIGN KEY (patient_id) REFERENCES patient(id);
ALTER TABLE patient_clinical_setting DROP COLUMN basal_rate_daily;
ALTER TABLE patient_clinical_setting DROP COLUMN hypo_threshold_mg_dl;
ALTER TABLE patient_clinical_setting DROP COLUMN hyper_threshold_mg_dl;
ALTER TABLE patient_clinical_setting DROP COLUMN sick_day_factor;
ALTER TABLE patient_clinical_setting DROP COLUMN exercise_factor;

ALTER TABLE medication DROP COLUMN dosage_mg;
ALTER TABLE medication DROP COLUMN frequency;
ALTER TABLE medication DROP COLUMN start_date;
ALTER TABLE medication DROP COLUMN end_date;
ALTER TABLE medication DROP COLUMN side_effects;
ALTER TABLE medication DROP COLUMN interaction_warnings;
ALTER TABLE medication RENAME COLUMN medication_name TO name;
ALTER TABLE medication ADD COLUMN type VARCHAR(50);
