-- 1. Insertar Tipos de Gráficos (Requerido por Trigger validate_chart_type)
INSERT INTO "type_chart" ("Id", "Name") VALUES 
(1, 'bars'),
(2, 'radar'),
(3, 'pie');

-- 2. Insertar Tipos de Gasto Periódico (Requerido por Trigger validate_periodic_type)
INSERT INTO "type_periodic" ("Id", "Name") VALUES 
(1, 'monthly'),
(2, 'quarterly'),
(3, 'yearly');

-- 4. Insertar Cuentas Bancarias
INSERT INTO "account" ("Id", "Id_User", "BankName", "Name", "AccountCode", "Number") VALUES 
(1, 3, 'BBVA', 'Cuenta Principal Ana', 'ACC-ANA-001', 'ES9100000000000000000001'),
(2, 3, 'ING', 'Ahorros Ana', 'ACC-ANA-002', 'ES9100000000000000000002');

INSERT INTO "establishment" ("Id", "Name") VALUES 
(1, 'MEDIA MARKT'),
(2, 'MERCADONA'),
(3, 'AMAZON'),
(4, 'ZARA'),
(5, 'EL CORTE INGLES'),
(6, 'ALDI'),
(7, 'CARREFOUR'),
(8, 'LIDL'),
(9, 'PRIMARK'),
(10, 'DECATHLON'),
(11, 'NETFLIX'),
(12, 'RENFE'),
(13, 'GIMNASIO FIT');

INSERT INTO "category" ("Id", "Id_User", "Name", "Icon", "CreatedAt", "DeletedAt") VALUES 
(1, 3, 'Supermercados', 'convenience_store', '2026-01-01', NULL),
(2, 3, 'Ocio y Suscripciones', 'cinema', '2026-01-01', NULL),
(3, 3, 'Transporte', 'bus', '2026-01-01', NULL),
(4, 3, 'Ropa y Accesorios', 'checkroom', '2026-02-01', NULL),
(5, 3, 'Electrónica', 'devices', '2026-01-01', NULL),
(6, 3, 'Suscripciones Temp', 'auto_delete', '2026-01-01', '2026-04-01');

INSERT INTO "goal" ("Id", "Id_Category", "Name", "TargetAmount", "CreatedAt", "DeletedAt") VALUES 
(1, 1, 'Límite Comida', 300.00, '2026-01-01', NULL),
(2, 2, 'Controlar Suscripciones', 50.00, '2026-01-01', NULL),
(3, 4, 'Presupuesto Rebajas', 150.00, '2026-02-01', NULL);

INSERT INTO "spending" ("Id", "Id_Category", "Id_Establishment", "Name", "Amount", "Date") VALUES 
-- Gastos de Enero 2026
(1, 1, 2, 'Compra Mercadona Semanal', 54.30, '2026-01-05'),
(2, 2, 11, 'Suscripción Netflix', 14.99, '2026-01-15'),
(3, 3, 12, 'Abono Transporte', 30.00, '2026-01-25'),
(4, 5, 1, 'Auriculares', 89.90, '2026-01-28'),
(11, 6, 11, 'Prueba HBO Max', 9.99, '2026-01-10'),
(13, 1, 6, 'Compra semanal Aldi', 42.15, '2026-01-12'),
(17, 2, 3, 'Libros Kindle', 12.50, '2026-01-20'),
(23, 5, 3, 'Ratón Gaming Amazon', 45.00, '2026-01-15'),

-- Gastos de Febrero 2026
(5, 1, 7, 'Compra Carrefour', 120.00, '2026-02-03'),
(6, 4, 4, 'Camisa y Pantalón Zara', 65.50, '2026-02-10'),
(7, 2, 13, 'Cuota Gimnasio', 35.00, '2026-02-20'),
(12, 6, 11, 'Disney Plus Promo', 8.99, '2026-02-10'),
(14, 1, 8, 'Fruta y verdura Lidl', 15.60, '2026-02-05'),
(18, 2, 5, 'Entradas Cine ECI', 18.00, '2026-02-14'),
(20, 4, 9, 'Ropa básica Primark', 35.00, '2026-02-15'),
(24, 5, 1, 'Monitor Curvo', 199.00, '2026-02-28'),

-- Gastos de Marzo 2026
(8, 1, 8, 'Compra Lidl', 45.20, '2026-03-05'),
(9, 2, 3, 'Juego Amazon', 59.99, '2026-03-12'),
(15, 1, 2, 'Mercadona Reposición', 22.10, '2026-03-10'),
(19, 2, 1, 'Videojuego MediaMarkt', 69.95, '2026-03-25'),
(21, 4, 4, 'Vestido Zara', 49.95, '2026-03-01'),
(25, 3, 12, 'Billete AVE', 75.00, '2026-03-15'),
(26, 3, 12, 'Taxi Estación', 15.40, '2026-03-15'),

-- Gastos de Abril 2026
(10, 1, 2, 'Compra Mercadona', 32.10, '2026-04-02'),
(16, 1, 7, 'Compra mensual Carrefour', 145.00, '2026-04-05'),
(22, 4, 10, 'Zapatillas Decathlon', 59.90, '2026-04-08');

-- Gasto ID 2: Netflix el día 15. Expiración debe ser el día 15 de un mes futuro.
-- Gasto ID 3: Abono el día 25. Expiración debe ser el día 25 de un mes futuro.
-- Gasto ID 7: Gimnasio el día 20. Expiración debe ser el día 20 de un mes futuro.
INSERT INTO "periodic_spending" ("Id", "Id_Spending", "Id_TypePeriodic", "Expiration", "LastPayment") VALUES 
(1, 2, 1, '2026-12-15', '2026-03-15'),  -- Netflix (Mensual, expira a final de año)
(2, 3, 1, '2026-06-25', '2026-03-25'),  -- Abono Transporte (Mensual, expira en junio)
(3, 7, 1, '2027-02-20', '2026-03-20');  -- Gimnasio (Mensual, expira el próximo año)

INSERT INTO "transaction" ("Id", "Id_Account", "Id_Spending", "TransactionCode") VALUES 
(1, 1, 1, 'TXN-000001'),
(2, 1, 2, 'TXN-000002'),
(3, 1, 3, 'TXN-000003'),
(4, 1, 4, 'TXN-000004'),
(5, 1, 5, 'TXN-000005'),
(6, 2, 6, 'TXN-000006'),
(7, 1, 7, 'TXN-000007'),
(8, 1, 8, 'TXN-000008'),
(9, 1, 9, 'TXN-000009'),
(10, 1, 10, 'TXN-000010');
