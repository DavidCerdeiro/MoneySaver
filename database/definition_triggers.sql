-- Clean up existing triggers and functions before creating new ones
DROP TRIGGER IF EXISTS trg_validate_periodic_type ON "type_periodic";
DROP TRIGGER IF EXISTS trg_validate_otp_purpose ON "purpose_otp";
DROP TRIGGER IF EXISTS trg_validate_periodic_expiration_date ON "periodic_spending";

DROP FUNCTION IF EXISTS validate_periodic_type();
DROP FUNCTION IF EXISTS validate_otp_purpose();
DROP FUNCTION IF EXISTS validate_periodic_expiration_date();

-- 1. Validate name in periodic spending type
CREATE OR REPLACE FUNCTION validate_periodic_type()
RETURNS trigger AS $$
BEGIN
    IF NEW."Name" NOT IN ('monthly', 'quarterly', 'yearly') THEN
        RAISE EXCEPTION 'Invalid periodic spending type (must be monthly, quarterly or yearly)'
        USING ERRCODE = 'P0003';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 2. Validate name in one-time password purpose
CREATE OR REPLACE FUNCTION validate_otp_purpose()
RETURNS trigger AS $$
BEGIN
    IF NEW."Name" NOT IN ('registerUser', 'loginUser', 'forgotPassword') THEN
        RAISE EXCEPTION 'Invalid OTP purpose (must be registerUser, loginUser or forgotPassword)'
        USING ERRCODE = 'P0004';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 3. Validate expiration date is today or later in periodic_spending
CREATE OR REPLACE FUNCTION validate_periodic_expiration_date()
RETURNS trigger AS $$
BEGIN
    IF NEW."Expiration" < CURRENT_DATE THEN
        RAISE EXCEPTION 'Expiration date cannot be earlier than today'
        USING ERRCODE = 'P0007';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- TRIGGERS

-- 1. Trigger for type_periodic
CREATE TRIGGER trg_validate_periodic_type
BEFORE INSERT OR UPDATE ON "type_periodic"
FOR EACH ROW
EXECUTE FUNCTION validate_periodic_type();

-- 2. Trigger for purpose_otp
CREATE TRIGGER trg_validate_otp_purpose
BEFORE INSERT OR UPDATE ON "purpose_otp"
FOR EACH ROW
EXECUTE FUNCTION validate_otp_purpose();

-- 3. Trigger for periodic_spending
CREATE TRIGGER trg_validate_periodic_expiration_date
BEFORE INSERT OR UPDATE ON "periodic_spending"
FOR EACH ROW
EXECUTE FUNCTION validate_periodic_expiration_date();
