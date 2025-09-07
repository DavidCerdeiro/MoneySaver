CREATE TABLE IF NOT EXISTS "type_chart" (
  "Id" serial PRIMARY KEY,
  "Name" varchar(8) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "user" (
  "Id" serial PRIMARY KEY,
  "Id_TypeChart" int NOT NULL DEFAULT 1,
  "Name" varchar(64) NOT NULL,
  "Surname" varchar(128) NOT NULL,
  "Email" varchar(64) NOT NULL,
  "Password" varchar(64) NOT NULL UNIQUE,
  "IsAuthenticated" boolean NOT NULL DEFAULT false,
  CONSTRAINT "RS_User__Id_TypeChart" FOREIGN KEY ("Id_TypeChart") REFERENCES "type_chart"("Id")
);

CREATE UNIQUE INDEX UX_User__Email ON "user"("Email");

CREATE TABLE IF NOT EXISTS "account" (
  "Id" serial PRIMARY KEY,
  "Id_User" int NOT NULL,
  "BankName" varchar(32) NOT NULL,
  "Name" varchar(32) NOT NULL,
  "AccountCode" varchar(64) NOT NULL UNIQUE,
  "Number" varchar(128) NOT NULL UNIQUE,
  CONSTRAINT "RS_Account__Id_User" FOREIGN KEY ("Id_User") REFERENCES "user"("Id") ON DELETE CASCADE
);

CREATE INDEX IX_Account__Id_User ON account("Id_User");

CREATE TABLE IF NOT EXISTS category (
  "Id" serial PRIMARY KEY,
  "Id_User" int NOT NULL,
  "Name" varchar(32) NOT NULL,
  "Icon" varchar(64) NOT NULL,
  "CreatedAt" date NOT NULL CHECK (date_trunc('month', "CreatedAt") = "CreatedAt"),
  "DeletedAt" date NULL CHECK (date_trunc('month', "DeletedAt") = "DeletedAt"),
  CONSTRAINT "RS_Category__Id_User" FOREIGN KEY ("Id_User") REFERENCES "user"("Id") ON DELETE CASCADE
);

CREATE UNIQUE INDEX UX_Category__Id_User_Name ON category ("Id_User", "Name") WHERE "DeletedAt" IS NULL;

CREATE INDEX IX_Category__Id_User ON category("Id_User");

CREATE TABLE IF NOT EXISTS "goal" (
  "Id" serial PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Name" varchar(32) NOT NULL,
  "TargetAmount" numeric(15,2) NOT NULL,
  "CreatedAt" date NOT NULL CHECK (date_trunc('month', "CreatedAt") = "CreatedAt"),
  "DeletedAt" date NULL CHECK (date_trunc('month', "DeletedAt") = "DeletedAt"),
  CONSTRAINT "RS_Goal__Id_Category" FOREIGN KEY ("Id_Category") REFERENCES category("Id") ON DELETE CASCADE,
  UNIQUE ("Id_Category", "Name")
);

CREATE INDEX IX_Goal__Id_Category ON "goal"("Id_Category");

CREATE TABLE IF NOT EXISTS establishment (
  "Id" serial PRIMARY KEY,
  "Name" varchar(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS spending (
  "Id" serial PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Id_Establishment" int,
  "Name" varchar(32) NOT NULL,
  "Amount" numeric(15,2) NOT NULL,
  "Date" Date NOT NULL,
  CONSTRAINT "RS_Spending__Id_Category" FOREIGN KEY ("Id_Category") REFERENCES category("Id"),
  CONSTRAINT "RS_Spending__Id_Establishment" FOREIGN KEY ("Id_Establishment") REFERENCES establishment("Id")
);

CREATE INDEX IX_Spending__Id_Category ON spending("Id_Category");

CREATE TABLE IF NOT EXISTS bill (
  "Id" serial PRIMARY KEY,
  "Id_Spending" int NOT NULL UNIQUE,
  "FileRoute" varchar(256) NOT NULL UNIQUE,
  CONSTRAINT "RS_Bill__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "transaction" (
  "Id" serial PRIMARY KEY,
  "Id_Account" int NOT NULL,
  "Id_Spending" int NOT NULL UNIQUE,
  "TransactionCode" varchar(64) NOT NULL UNIQUE, 
  CONSTRAINT "RS_Transaction__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id") ON DELETE CASCADE,
  CONSTRAINT "RS_Transaction__Id_Account" FOREIGN KEY ("Id_Account") REFERENCES "account"("Id") ON DELETE CASCADE
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
  "LastPayment" Date NOT NULL,
  CONSTRAINT "RS_PeriodicSpending__Id_Spending" FOREIGN KEY ("Id_Spending") REFERENCES spending("Id") ON DELETE CASCADE,
  CONSTRAINT "RS_PeriodicSpending__Id_TypePeriodic" FOREIGN KEY ("Id_TypePeriodic") REFERENCES "type_periodic"("Id")
);
