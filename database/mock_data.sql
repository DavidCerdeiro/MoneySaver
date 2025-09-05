INSERT INTO "category" ("Id_User", "Name", "Icon", "CreatedAt", "DeletedAt")
VALUES
(1, 'Food', 'heavy_dollar_sign', '2025-01-01', '2025-01-01'),
(1, 'Transport', 'heavy_dollar_sign', '2025-02-01', '2025-04-01'),
(1, 'Utilities', 'heavy_dollar_sign', '2025-04-01', NULL);



INSERT INTO "establishment" ("Name")
VALUES
('Action');

INSERT INTO "goal" ("Id_Category", "Name", "TargetAmount", "CreatedAt", "DeletedAt")
VALUES
(1, 'Save for vacation', 1000, '2025-01-01', '2025-01-01'),
(2, 'Buy a new car', 20000, '2025-02-01', '2025-03-01'),
(3, 'Home renovation', 15000, '2025-04-01', NULL);

INSERT INTO "spending" ("Id_Category", "Name", "Amount", "Date", "IsPeriodic")
VALUES
(1, 'Compra en Mercadona', 200, '2025-01-15', FALSE),
(1, 'Compra en Carrefour', 150, '2025-01-20', FALSE),
(2, 'Gasolina', 500, '2025-02-10', FALSE),
(2, 'Mantenimiento del coche', 300, '2025-02-15', FALSE),
(3, 'Reforma del baño', 1000, '2025-04-05', FALSE),
(3, 'Periodica mensual', 500, '2025-04-10', TRUE),
(3, 'Periodica trimestral', 1500, '2025-04-12', TRUE),
(3, 'Periodica anual', 6000, '2025-04-15', TRUE);

INSERT INTO "spending" ("Id_Category", "Name", "Amount", "Date", "IsPeriodic")
VALUES
(4, 'Compra en Aldi', 300, '2025-01-12', FALSE),
(4, 'Compra en Amazon', 200, '2025-01-20', FALSE),
(4, 'Gasolina', 400.50, '2025-02-10', FALSE),
(4, 'Papa', 100, '2025-02-18', FALSE),
(4, 'Reforma del baño otra vez', 2000, '2025-04-05', FALSE),
(4, 'Este mes', 500, '2025-08-10', FALSE),
(4, 'Este mes 2', 15, '2025-08-12', FALSE),
(4, 'Este mes 3', 30, '2025-08-15', FALSE);

INSERT INTO "periodic_spending" ("Id_Spending", "Id_TypePeriodic", "Expiration", "LastPayment")
VALUES
(6, 1, '2025-09-30', '2025-08-30'),
(7, 2, '2025-09-30', '2025-06-30'),
(8, 3, '2025-09-30', '2024-09-30');


INSERT INTO "spending" ("Id_Category", "Id_Establishment", "Name", "Amount", "Date", "IsPeriodic")
VALUES
(8, 5, 'SAVE THE CHANGE', 50, '2025-09-02', FALSE);

INSERT INTO "transaction" ("Id_Account", "Id_Spending", "TransactionCode")
VALUES
(2, 25, 'TXN-2025-09-02');

DELETE FROM "establishment" WHERE "Id" = 3;