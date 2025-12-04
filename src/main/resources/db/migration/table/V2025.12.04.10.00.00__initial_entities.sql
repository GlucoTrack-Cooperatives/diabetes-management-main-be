CREATE TABLE "user" (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password_hash VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL,
                        is_active BOOLEAN DEFAULT TRUE,
                        created_at TIMESTAMP NOT NULL DEFAULT now(),
                        updated_at TIMESTAMP
);

CREATE TABLE patient (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         phone_numbers VARCHAR(255) NOT NULL,
                         dob DATE NOT NULL,
                         diagnosis_date DATE NOT NULL,
                         emergency_contact_phone VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE physician (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           specialty VARCHAR(255) NOT NULL,
                           license_number VARCHAR(255) NOT NULL UNIQUE,
                           clinic_name VARCHAR(255) NOT NULL,
                           created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE patient_clinical_setting (
                                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                          target_range_low INTEGER NOT NULL,
                                          target_range_high INTEGER NOT NULL,
                                          insulin_carb_ratio REAL NOT NULL,
                                          correction_factor REAL NOT NULL,
                                          basal_rate_daily REAL NOT NULL,
                                          hypo_threshold_mg_dl INTEGER NOT NULL DEFAULT 70,
                                          hyper_threshold_mg_dl INTEGER NOT NULL DEFAULT 180,
                                          sick_day_factor REAL NOT NULL DEFAULT 1.0,
                                          exercise_factor REAL NOT NULL DEFAULT 0.8,
                                          created_at TIMESTAMP NOT NULL DEFAULT now(),
                                          updated_at TIMESTAMP
);

CREATE TABLE activity_log (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              date DATE NOT NULL,
                              step_count INTEGER NOT NULL DEFAULT 0,
                              active_energy_burned REAL NOT NULL DEFAULT 0.0,
                              exercise_minutes INTEGER NOT NULL DEFAULT 0,
                              sleep_duration_minutes INTEGER NOT NULL DEFAULT 0,
                              sleep_start_time TIMESTAMP,
                              sleep_end_time TIMESTAMP,
                              created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE biometrics_log (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                date DATE NOT NULL,
                                weight_kg REAL NOT NULL,
                                water_intake_ml INTEGER NOT NULL,
                                created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE alert (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       timestamp TIMESTAMP NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       message TEXT NOT NULL,
                       is_acknowledged BOOLEAN DEFAULT FALSE,
                       acknowledged_at TIMESTAMP,
                       created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE audit_log (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           action VARCHAR(255) NOT NULL,
                           resource_type VARCHAR(255) NOT NULL,
                           resource_id UUID NOT NULL,
                           timestamp TIMESTAMP NOT NULL,
                           ip_address VARCHAR(255) NOT NULL,
                           changes JSONB,
                           created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE chat_thread (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             last_message_at TIMESTAMP,
                             created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE chat_message (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              content TEXT NOT NULL,
                              timestamp TIMESTAMP NOT NULL,
                              is_read BOOLEAN DEFAULT FALSE,
                              created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE daily_patient_stat (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    date DATE NOT NULL,
                                    average_glucose REAL NOT NULL,
                                    time_in_range_percent REAL NOT NULL,
                                    time_below_range_percent REAL NOT NULL,
                                    time_above_range_percent REAL NOT NULL DEFAULT 0.0,
                                    total_carbs INTEGER NOT NULL DEFAULT 0,
                                    total_insulin REAL NOT NULL DEFAULT 0.0,
                                    risk_score INTEGER NOT NULL DEFAULT 0,
                                    created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE food_log (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          timestamp TIMESTAMP NOT NULL,
                          meal_type VARCHAR(50) NOT NULL,
                          carbs_grams INTEGER NOT NULL,
                          calories INTEGER NOT NULL,
                          food_name VARCHAR(255) NOT NULL,
                          image_url VARCHAR(255),
                          input_method VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE glucose_reading (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 timestamp TIMESTAMP NOT NULL,
                                 value INTEGER NOT NULL,
                                 trend VARCHAR(50) NOT NULL,
                                 source VARCHAR(50) NOT NULL,
                                 created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE health_event (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              timestamp TIMESTAMP NOT NULL,
                              category VARCHAR(50) NOT NULL,
                              severity INTEGER NOT NULL DEFAULT 3,
                              note TEXT,
                              created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE insulin_dose (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              timestamp TIMESTAMP NOT NULL,
                              insulin_type VARCHAR(50) NOT NULL,
                              units REAL NOT NULL,
                              is_correction BOOLEAN DEFAULT FALSE,
                              created_at TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE medication (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            medication_name VARCHAR(255) NOT NULL,
                            dosage_mg REAL NOT NULL,
                            frequency VARCHAR(50) NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE,
                            side_effects TEXT,
                            interaction_warnings JSONB,
                            created_at TIMESTAMP NOT NULL DEFAULT now()
);
