ALTER TABLE "user" ADD COLUMN first_name varchar(255);
ALTER TABLE "user" ADD COLUMN surname varchar(255);

-- Simple split: first token -> first_name, remainder -> surname
UPDATE "user"
SET first_name = split_part(full_name, ' ', 1),
    surname = NULLIF(btrim(substr(full_name, length(split_part(full_name, ' ', 1)) + 1)), '');

-- If surname is still null (single-word full_name), set surname to empty string or repeat first_name
UPDATE "user"
SET surname = ''
WHERE surname IS NULL;

ALTER TABLE "user" ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE "user" ALTER COLUMN surname SET NOT NULL;

ALTER TABLE "user" DROP COLUMN full_name;