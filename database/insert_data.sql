INSERT INTO "user" ("Name", "Surname", "Email", "Password")
VALUES 
('David', 'Cerdeiro', 'davidcergall22@gmail.com', 'pass1234'),
('Jesulín', 'De Ubrique', 'jesulito@gmail.com', 'illoquepasa'),
('Cristiano', 'Ronaldo Dos Santos Aveiro', 'elBicho@hotmail.com', 'clave2025');


INSERT INTO category ("Id_User", "Name", "TotalSpending", "Icon")
VALUES
(1, 'Supermercados', 0.00, 2),
(1, 'Transporte', 0.00, 0),
(2, 'Restaurantes', 0.00, 1),
(3, 'Suscripciones', 0.00, 3);

INSERT INTO "bank_account" ("Id_User", "BankName", "AccessToken")
VALUES
(1, 'BBVA', 'token123abc'),
(2, 'Santander', 'token456def'),
(3, 'CaixaBank', 'token789ghi');

INSERT INTO "type_periodic" ("Name")
VALUES
('Monthly'),
('Quarterly'),
('Yearly');

INSERT INTO street ("Name", "Number")   
VALUES
('Gran Vía', 12),
('Calle Feria', 9),
('Santo Domingo', 16);

INSERT INTO "address" ("Country", "City", "Id_Street")
VALUES
('España', 'Madrid', 1),
('España', 'Sevilla', 2),
('España', 'Cádiz', 3);


INSERT INTO establishment ("Id_Address", "Id_Category", "Name")
VALUES
(1, 1, 'Mercadona'),
(2, 2, 'Uber'),
(3, NULL, 'KFC');

INSERT INTO spending ("Id_Category", "Id_User", "Name", "Amount", "Date", "IsPeriodic")
VALUES
(1, 1, 1, 'Compra semanal', 55.20, '2025-06-01', false),
(2, 2, 2, 'Viaje aeropuerto', 30.00, '2025-05-10', false),
(3, 3, 3, 'Cena rápida', 18.50, '2025-05-12', false),
(4, NULL, 3, 'Spotify', 9.99, '2025-05-01', true);

INSERT INTO bill ("Id_Spending", "FileRoute")
VALUES
(1, '/files/bills/compra_semana.pdf'),
(2, '/files/bills/viaje_aeropuerto.pdf');


INSERT INTO "bank_transaction" ("Id_BankAccount", "Id_Spending")
VALUES
(1, 1),
(2, 2),
(3, 3);

INSERT INTO "periodic_spending" ("Id_Spending", "Id_TypePeriodic", "Expiration")
VALUES
(4, 1, '2025-06-10');

INSERT INTO "savings_goal" ("Id_Category", "Name", "TargetAmount")
VALUES
(1, 'Vacaciones', 1500.00),
(2, 'Entrada del piso', 10000.00),
(3, 'Curso programación', 500.00);

INSERT INTO "purpose_otp" ("Name")
VALUES
('forgotPassword'),
('registerUser'),
('loginUser');