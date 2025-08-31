INSERT INTO "type_periodic" ("Name")
VALUES
('monthly'),
('quarterly'),
('yearly');


INSERT INTO "type_chart" ("Name")
VALUES
('bars'),
('radar'),
('pie');

INSERT INTO "user" ("Name", "Surname", "Email", "Password")
VALUES ('Walter', 'White', 'heisenberg@gmail.com', 'password123');

UPDATE "user" SET "Id_TypeChart" = NULL, "Name" = 'Walter2', "Surname" = 'White2', "Password" = 'newpassword123', "Email" = 'heisenberg2@gmail.com', "IsAuthenticated" = FALSE WHERE "Id" = 7;

INSERT INTO "account" ("BankName", "Name", "Number")
VALUES ('Bank of America', 'Cuenta de ahorros', 'ES1234567890');
SELECT "Id", 'Bank of America', 'Walter2', '1234567890' FROM "user" WHERE "Id" = 7;

INSERT INTO "bill" ("FileRoute")
VALUES ('/path/to/bill.pdf');