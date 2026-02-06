CREATE TABLE patient_appointment (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    next_due_date TIMESTAMP NOT NULL,
    notes TEXT,
    creation_timestamp TIMESTAMP,
    modification_timestamp TIMESTAMP,
    CONSTRAINT fk_patient_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient(id)
);

CREATE INDEX idx_patient_appointment_patient_id ON patient_appointment(patient_id);
