ALTER TABLE "user" DROP COLUMN created_at;
ALTER TABLE "user" DROP COLUMN updated_at;
ALTER TABLE "user" ADD COLUMN creation_timestamp TIMESTAMP;
ALTER TABLE "user" ADD COLUMN modification_timestamp TIMESTAMP;
ALTER TABLE "user" RENAME COLUMN password_hash TO password;
ALTER TABLE "user" ADD COLUMN full_name VARCHAR(255);
