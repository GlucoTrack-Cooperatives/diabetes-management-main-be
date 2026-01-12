CREATE TABLE fcm_tokens (
                            id BIGSERIAL PRIMARY KEY,
                            user_id UUID NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
                            token TEXT NOT NULL UNIQUE,
                            last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);