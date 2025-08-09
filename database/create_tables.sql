CREATE TABLE IF NOT EXISTS "user" (
  "Id" serial PRIMARY KEY,
  "Name" varchar(64) NOT NULL,
  "Surname" varchar(128) NOT NULL,
  "Email" varchar(32) NOT NULL,
  "Password" varchar(64) NOT NULL,
  "IsAuthenticated" boolean NOT NULL DEFAULT false
);

CREATE UNIQUE INDEX UX_User__Email ON "user"("Email");

CREATE TABLE IF NOT EXISTS "account" (
  "Id" serial PRIMARY KEY,
  "Id_User" int NOT NULL,
  "BankName" varchar(32) NOT NULL,
  "AccessToken" varchar(128) NOT NULL UNIQUE,
  CONSTRAINT "RS_Account__Id_User" FOREIGN KEY ("Id_User") REFERENCES "user"("Id")
);

CREATE INDEX IX_Account__Id_User ON account("Id_User");

CREATE TABLE IF NOT EXISTS category (
  "Id" serial PRIMARY KEY,
  "Id_User" int NOT NULL,
  "Name" varchar(32) NOT NULL,
  "Icon" varchar(64) NOT NULL,
  "IsDeleted" boolean NOT NULL DEFAULT false,
  CONSTRAINT "RS_Category__Id_User" FOREIGN KEY ("Id_User") REFERENCES "user"("Id"),
  UNIQUE ("Id_User", "Name")
);

CREATE INDEX IX_Category__Id_User ON category("Id_User");

CREATE TABLE IF NOT EXISTS "goal" (
  "Id" serial PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Name" varchar(32) NOT NULL,
  "TargetAmount" numeric(15,2) NOT NULL,
  CONSTRAINT "RS_Goal__Id_Category" FOREIGN KEY ("Id_Category") REFERENCES category("Id"),
  UNIQUE ("Id_Category", "Name")
);

CREATE INDEX IX_Goal__Id_Category ON "goal"("Id_Category");

CREATE TABLE IF NOT EXISTS "region" (
  "Id" serial PRIMARY KEY,
  "Country" varchar(16) NOT NULL,
  "City" varchar(16) NOT NULL,
  UNIQUE ("Country", "City")
);

CREATE TABLE IF NOT EXISTS establishment (
  "Id" serial PRIMARY KEY,
  "Id_Region" int,
  "Name" varchar(64) NOT NULL,
  CONSTRAINT "RS_Establishment__Id_Region" FOREIGN KEY ("Id_Region") REFERENCES "region"("Id")
);

CREATE TABLE IF NOT EXISTS spending (
  "Id" serial PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Id_Establishment" int,
  "Id_User" int NOT NULL,
  "Name" varchar(64) NOT NULL,
  "Amount" numeric(15,2) NOT NULL,
  "Date" Date NOT NULL,
  "IsPeriodic" boolean NOT NULL,
  CONSTRAINT "RS_Spending__Id_Category" FOREIGN KEY ("Id_Category") REFERENCES category("Id"),
  CONSTRAINT "RS_Spending__Id_Establishment" FOREIGN KEY ("Id_Establishment") REFERENCES establishment("Id"),
  CONSTRAINT "RS_Spending__Id_User" FOREIGN KEY ("Id_User") REFERENCES "user"("Id")
);

CREATE INDEX IX_Spending__Id_User ON spending("Id_User");
CREATE INDEX IX_Spending__Id_Category ON spending("Id_Category");

CREATE TABLE IF NOT EXISTS bill (
  "Id" serial PRIMARY KEY,
  "Id_Spending" int NOT NULL UNIQUE,
  "FileRoute" varchar(256) NOT NULL UNIQUE,
  CONSTRAINT "RS_Bill__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id")
);

CREATE TABLE IF NOT EXISTS "transaction" (
  "Id" serial PRIMARY KEY,
  "Id_Account" int NOT NULL,
  "Id_Spending" int NOT NULL UNIQUE,
  CONSTRAINT "RS_Transaction__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id"),
  CONSTRAINT "RS_Transaction__Id_Account" FOREIGN KEY ("Id_Account") REFERENCES "account"("Id")
);

CREATE TABLE IF NOT EXISTS "type_periodic" (
  "Id" serial PRIMARY KEY,
  "Name" varchar(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "periodic_spending" (
  "Id" serial PRIMARY KEY,
  "Id_Spending" int NOT NULL UNIQUE,
  "Id_TypePeriodic" int NOT NULL,
  "Expiration" Date NOT NULL,
  "Last_Payment" Date NOT NULL,
  CONSTRAINT "RS_PeriodicSpending__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id"),
  CONSTRAINT "RS_PeriodicSpending__Id_TypePeriodic" FOREIGN KEY ("Id_TypePeriodic") REFERENCES "type_periodic"("Id")
);
