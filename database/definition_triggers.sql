-- Clean up existing triggers and functions before creating new ones
DROP TRIGGER IF EXISTS TR_Type_Periodic_BI ON "type_periodic";
DROP TRIGGER IF EXISTS TR_Periodic_Spending_BI ON "periodic_spending";
DROP TRIGGER IF EXISTS TR_Type_Chart_BI ON "type_chart";
DROP TRIGGER IF EXISTS TR_Category_CreatedAt_BU ON "category";
DROP TRIGGER IF EXISTS TR_Goal_CreatedAt_BU ON "goal";

DROP FUNCTION IF EXISTS validate_periodic_type();
DROP FUNCTION IF EXISTS validate_periodic_spending();
DROP FUNCTION IF EXISTS validate_chart_type();
DROP FUNCTION IF EXISTS validate_createdat_immutable();

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

-- 2. Validate name in periodic spending type
CREATE OR REPLACE FUNCTION validate_chart_type()
RETURNS trigger AS $$
BEGIN
    IF NEW."Name" NOT IN ('bars', 'radar', 'pie') THEN
        RAISE EXCEPTION 'Invalid periodic spending type (must be bars, radar or pie)'
        USING ERRCODE = 'P0004';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- 3. Validate expiration date is today or later and matches the spending day
CREATE OR REPLACE FUNCTION validate_periodic_spending()
RETURNS TRIGGER AS $$
DECLARE
    spending_day INTEGER;
    expiration_day INTEGER;
BEGIN
    -- 1. Validte that expiration date is today or later
    IF NEW."Expiration" < CURRENT_DATE THEN
        RAISE EXCEPTION 'Expiration date cannot be earlier than today'
        USING ERRCODE = 'P0006';
    END IF;

    -- 2. Validate that expiration date matches the spending day
    SELECT EXTRACT(DAY FROM "Date") INTO spending_day
    FROM spending
    WHERE "Id" = NEW."Id_Spending";

    expiration_day := EXTRACT(DAY FROM NEW."Expiration");

    IF spending_day != expiration_day THEN
        RAISE EXCEPTION 'Expiration day (%) does not match spending day (%).', expiration_day, spending_day
        USING ERRCODE = 'P0009';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 4. Validate CreatedAt is immutable
CREATE OR REPLACE FUNCTION validate_createdat_immutable()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW."CreatedAt" IS DISTINCT FROM OLD."CreatedAt" THEN
        RAISE EXCEPTION 'El atributo "CreatedAt" no es modificable'
        USING ERRCODE = 'P0010';
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

-- 2. Trigger for type_periodic
CREATE TRIGGER TR_Type_Chart_BI
BEFORE INSERT ON "type_chart"
FOR EACH ROW
EXECUTE FUNCTION validate_chart_type();

-- 3. Trigger for periodic_spending
CREATE TRIGGER TR_Periodic_Spending_BI
BEFORE INSERT ON "periodic_spending"
FOR EACH ROW
EXECUTE FUNCTION validate_periodic_spending();

-- 4. Trigger for category
CREATE TRIGGER TR_Category_CreatedAt_BU
BEFORE UPDATE ON "category"
FOR EACH ROW
EXECUTE FUNCTION validate_createdat_immutable();

-- 5. Trigger for goal
CREATE TRIGGER TR_Goal_CreatedAt_BU
BEFORE UPDATE ON "goal"
FOR EACH ROW
EXECUTE FUNCTION validate_createdat_immutable();

-- Function to clone demo user data
CREATE OR REPLACE FUNCTION clone_demo_user(p_template_email VARCHAR, p_new_email VARCHAR, p_new_password VARCHAR)
RETURNS INT AS $$
DECLARE
    v_template_user_id INT;
    v_new_user_id INT;
BEGIN
    -- 1. Obtain template user ID
    SELECT "Id" INTO v_template_user_id FROM "user" WHERE "Email" = p_template_email;
    IF v_template_user_id IS NULL THEN
        RAISE EXCEPTION 'Template user not found';
    END IF;

    -- 2. Create new user
    INSERT INTO "user" ("Id_TypeChart", "Name", "Surname", "Email", "Password", "IsAuthenticated")
    SELECT "Id_TypeChart", 'Demo', 'User', p_new_email, p_new_password, true
    FROM "user" WHERE "Id" = v_template_user_id
    RETURNING "Id" INTO v_new_user_id;

    -- 3. Clone Accounts
    CREATE TEMP TABLE temp_account_map ON COMMIT DROP AS
    SELECT "Id" AS old_id, nextval('"account_Id_seq"')AS new_id FROM "account" WHERE "Id_User" = v_template_user_id;

    INSERT INTO "account" ("Id", "Id_User", "BankName", "Name", "AccountCode", "Number")
    SELECT m.new_id, v_new_user_id, a."BankName", a."Name", a."AccountCode" || '_' || v_new_user_id, a."Number" || '_' || v_new_user_id
    FROM "account" a JOIN temp_account_map m ON a."Id" = m.old_id;

    -- 4. Clone Categories
    CREATE TEMP TABLE temp_category_map ON COMMIT DROP AS
    SELECT "Id" AS old_id, nextval('"category_Id_seq"') AS new_id FROM "category" WHERE "Id_User" = v_template_user_id;

    INSERT INTO "category" ("Id", "Id_User", "Name", "Icon", "CreatedAt", "DeletedAt")
    SELECT m.new_id, v_new_user_id, c."Name", c."Icon", c."CreatedAt", c."DeletedAt"
    FROM "category" c JOIN temp_category_map m ON c."Id" = m.old_id;

    -- 5. Clone Goals
    INSERT INTO "goal" ("Id_Category", "Name", "TargetAmount", "CreatedAt", "DeletedAt")
    SELECT m.new_id, g."Name", g."TargetAmount", g."CreatedAt", g."DeletedAt"
    FROM "goal" g JOIN temp_category_map m ON g."Id_Category" = m.old_id;

    -- 6. Clone Spending
    CREATE TEMP TABLE temp_spending_map ON COMMIT DROP AS
    SELECT s."Id" AS old_id, nextval('"spending_Id_seq"') AS new_id
    FROM "spending" s JOIN temp_category_map c ON s."Id_Category" = c.old_id;

    INSERT INTO "spending" ("Id", "Id_Category", "Id_Establishment", "Name", "Amount", "Date")
    SELECT m.new_id, cmap.new_id, s."Id_Establishment", s."Name", s."Amount", s."Date"
    FROM "spending" s
    JOIN temp_spending_map m ON s."Id" = m.old_id
    JOIN temp_category_map cmap ON s."Id_Category" = cmap.old_id;

    -- 7. Clone Bills
    INSERT INTO "bill" ("Id_Spending", "FileRoute")
    SELECT m.new_id, b."FileRoute" || '_' || m.new_id
    FROM "bill" b JOIN temp_spending_map m ON b."Id_Spending" = m.old_id;

    -- 8. Clone Transactions
    INSERT INTO "transaction" ("Id_Account", "Id_Spending", "TransactionCode")
    SELECT amap.new_id, smap.new_id, t."TransactionCode" || '_' || smap.new_id
    FROM "transaction" t
    JOIN temp_spending_map smap ON t."Id_Spending" = smap.old_id
    JOIN temp_account_map amap ON t."Id_Account" = amap.old_id;

    -- 9. Clone Periodic Spending
    INSERT INTO "periodic_spending" ("Id_Spending", "Id_TypePeriodic", "Expiration", "LastPayment")
    SELECT m.new_id, p."Id_TypePeriodic", p."Expiration", p."LastPayment"
    FROM "periodic_spending" p JOIN temp_spending_map m ON p."Id_Spending" = m.old_id;

    RETURN v_new_user_id;
END;
$$ LANGUAGE plpgsql;