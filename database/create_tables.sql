CREATE TABLE IF NOT EXISTS "user" (
  "Id" serial PRIMARY KEY,
  "Name" varchar(64) NOT NULL,
  "Surname" varchar(128) NOT NULL,
  "Email" varchar(32) NOT NULL UNIQUE,
  "Password" varchar(64) NOT NULL.
  "IsAuthenticated" boolean NOT NULL DEFAULT false,
);

CREATE TABLE IF NOT EXISTS "purpose_otp"(
  "Id" serial  PRIMARY KEY,
  "Name" varchar(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "one_time_password" (
  "Id" serial PRIMARY KEY,
  "Id_PurposeOTP" int NOT NULL,
  "Email" varchar(32) NOT NULL,
  "Token" varchar(6) NOT NULL,
  "Expiration" timestamp NOT NULL,
  "Purpose" varchar(16) NOT NULL,
  FOREIGN KEY ("Id_PurposeOTP") REFERENCES "purpose_otp"("Id")
  CONSTRAINT unique_email_purpose UNIQUE ("Email", "Purpose")
);



CREATE TABLE IF NOT EXISTS "bank_account" (
  "Id" serial  PRIMARY KEY,
  "Id_User" int NOT NULL,
  "BankName" varchar(32) NOT NULL,
  "AccessToken" varchar(128) NOT NULL UNIQUE,
  FOREIGN KEY ("Id_User") REFERENCES "user"("Id")  
);

CREATE INDEX IX_Bank_Account__Id_User ON bank_account("Id_User");

CREATE TABLE IF NOT EXISTS category (
  "Id" serial  PRIMARY KEY,
  "Id_User" int NOT NULL,
  "Name" varchar(32) NOT NULL UNIQUE,
  "TotalSpending"  numeric(15,2) NOT NULL DEFAULT 0.00,
  "Icon" varchar(64) NOT NULL,
  FOREIGN KEY ("Id_User") REFERENCES "user"("Id") 
);

CREATE INDEX IX_Category__Id_User ON category("Id_User");

CREATE TABLE IF NOT EXISTS "savings_goal" (
  "Id" serial  PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Name" varchar(32) NOT NULL UNIQUE,
  "TargetAmount" numeric(15,2) NOT NULL,
  FOREIGN KEY ("Id_Category") REFERENCES category("Id")
);

CREATE TABLE IF NOT EXISTS street (
  "Id" serial  PRIMARY KEY,
  "Name" varchar(32) NOT NULL,
  "Number" int NOT NULL
);

CREATE TABLE IF NOT EXISTS "address" (
  "Id" serial  PRIMARY KEY,
  "Id_Street" int NOT NULL UNIQUE,
  "Country" varchar(16) NOT NULL,
  "City" varchar(16) NOT NULL,
  FOREIGN KEY ("Id_Street") REFERENCES street("Id") 
);

CREATE TABLE IF NOT EXISTS establishment (
  "Id" serial  PRIMARY KEY,
  "Id_Address" int UNIQUE,
  "Id_Category" int,
  "Name" varchar(64) NOT NULL,
  FOREIGN KEY ("Id_Category") REFERENCES category("Id"),
  FOREIGN KEY ("Id_Address") REFERENCES "address"("Id")
);

CREATE TABLE IF NOT EXISTS spending (
  "Id" serial  PRIMARY KEY,
  "Id_Category" int NOT NULL,
  "Id_Establishment" int,
  "Id_User" int NOT NULL,
  "Name" varchar(64) NOT NULL,
  "Amount" numeric(15,2) NOT NULL,
  "Date" Date NOT NULL,
  "IsPeriodic" boolean NOT NULL,
  FOREIGN KEY ("Id_Category") REFERENCES category("Id"),
  FOREIGN KEY ("Id_Establishment") REFERENCES establishment("Id"),
  FOREIGN KEY ("Id_User") REFERENCES "user"("Id")
);

CREATE INDEX IX_Spending__Id_User ON spending("Id_User");

CREATE TABLE IF NOT EXISTS bill (
  "Id" serial  PRIMARY KEY,
  "Id_Spending" int NOT NULL UNIQUE,
  "FileRoute" varchar(256) NOT NULL UNIQUE,
  FOREIGN KEY ("Id_Spending") REFERENCES spending("Id")
);

CREATE TABLE IF NOT EXISTS "bank_transaction"(
  "Id" serial  PRIMARY KEY,
  "Id_BankAccount" int NOT NULL,
  "Id_Spending" int NOT NULL UNIQUE,
  FOREIGN KEY ("Id_Spending") REFERENCES spending("Id"),
  FOREIGN KEY ("Id_BankAccount") REFERENCES "bank_account"("Id")
);

CREATE TABLE IF NOT EXISTS "type_periodic"(
  "Id" serial  PRIMARY KEY,
  "Name" varchar(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "periodic_spending"(
  "Id" serial  PRIMARY KEY,
  "Id_Spending" int NOT NULL UNIQUE,
  "Id_TypePeriodic" int NOT NULL,
  "Expiration" Date NOT NULL,
  "Last_Execution" Date NOT NULL,
  FOREIGN KEY ("Id_Spending") REFERENCES spending("Id"),
  FOREIGN KEY ("Id_TypePeriodic") REFERENCES "type_periodic"("Id")
);

