-- Clean up existing triggers and functions before creating new ones
DROP TRIGGER IF EXISTS TR_Type_Periodic_BI ON "type_periodic";
DROP TRIGGER IF EXISTS TR_Periodic_Spending_BI ON "periodic_spending";

DROP FUNCTION IF EXISTS validate_periodic_type();
DROP FUNCTION IF EXISTS validate_periodic_spending();

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


-- 2. Validate expiration date is today or later and matches the spending day
CREATE OR REPLACE FUNCTION validate_periodic_spending()
RETURNS TRIGGER AS $$
DECLARE
    spending_day INTEGER;
    expiration_day INTEGER;
BEGIN
    -- 1. Validte that expiration date is today or later
    IF NEW."Expiration" < CURRENT_DATE THEN
        RAISE EXCEPTION 'Expiration date cannot be earlier than today'
        USING ERRCODE = 'P0007';
    END IF;

    -- 2. Validate that expiration date matches the spending day
    SELECT EXTRACT(DAY FROM "Date") INTO spending_day
    FROM spending
    WHERE "Id" = NEW."Id_Spending";

    expiration_day := EXTRACT(DAY FROM NEW."Expiration");

    IF spending_day != expiration_day THEN
        RAISE EXCEPTION 'El día de expiración (%) no coincide con el día del gasto (%).', expiration_day, spending_day
        USING ERRCODE = 'P0008';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGERS

-- 1. Trigger for type_periodic
CREATE TRIGGER TR_Type_Periodic_BI
BEFORE INSERT ON "type_periodic"
FOR EACH ROW
EXECUTE FUNCTION validate_periodic_type();

-- 2. Trigger for periodic_spending
CREATE TRIGGER TR_Periodic_Spending_BI
BEFORE INSERT ON "periodic_spending"
FOR EACH ROW
EXECUTE FUNCTION validate_periodic_spending();
