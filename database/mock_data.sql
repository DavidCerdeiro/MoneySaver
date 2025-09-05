INSERT INTO "category" ("Id_User", "Name", "Icon", "CreatedAt", "DeletedAt")
VALUES
(1, 'Supermercados', 'heavy_dollar_sign', '2025-03-01', NULL),
(1, 'Ropa', 'heavy_dollar_sign', '2025-02-01', NULL),
(1, 'Cenas', 'heavy_dollar_sign', '2025-02-01', NULL),
(1, 'Donaciones', 'heavy_dollar_sign', '2025-01-01', NULL),
(1, 'Casa', 'heavy_dollar_sign', '2025-06-01', NULL)
(1, 'Varios', 'heavy_dollar_sign', '2025-05-01', '2025-07-01'),
(1, 'Transporte', 'heavy_dollar_sign', '2025-05-01', '2025-05-01'),
(1, 'Cine', 'heavy_dollar_sign', '2025-02-01', '2025-05-01'),
(1, 'Tecnología', 'heavy_dollar_sign', '2025-02-01', '2025-08-01');





INSERT INTO "establishment" ("Name")
VALUES
('ACTION'),
('ALDI'),
('AMAZON'),
('ZARA'),
('BERSHKA'),
('SAVE THE CHANGE'),
('MERCADONA'),
('EL FARO'),
('PROVENZAL'),
('EL CAFE DE ANA');

INSERT INTO "goal" ("Id_Category", "Name", "TargetAmount", "CreatedAt", "DeletedAt")
VALUES
(10, 'Reducir gastos en supermercados', 600, '2025-01-01', NULL),
(15, 'Menos compras varias', 50, '2025-02-01', '2025-07-01'),
(11, 'Reduccion de armario', 60, '2025-02-01', NULL);

INSERT INTO "spending" ("Id_Category", "Name", "Amount", "Date", "IsPeriodic", "Id_Establishment") VALUES
-- Supermercados (Id 10)
(10, 'Compra en Mercadona', 120.50, '2025-03-10', FALSE, 22),
(10, 'Compra en Aldi', 85.30, '2025-04-12', FALSE, 17),
(10, 'Compra en Amazon Fresh', 95.00, '2025-05-08', FALSE, 18),
(10, 'Compra en Mercadona', 150.00, '2025-06-14', FALSE, 22),
(10, 'Compra en Carrefour', 110.20, '2025-07-16', FALSE, NULL),
(10, 'Compra en Lidl', 98.40, '2025-08-20', FALSE, NULL),
(10, 'Compra en Mercadona', 130.75, '2025-09-05', FALSE, 22),
(10, 'Compra en Aldi', 90.00, '2025-03-28', FALSE, 17),
(10, 'Compra en Mercadona', 125.40, '2025-04-25', FALSE, 22),
(10, 'Compra en Carrefour', 135.00, '2025-05-21', FALSE, NULL),
(10, 'Compra en Amazon Fresh', 80.60, '2025-06-30', FALSE, 18),
(10, 'Compra en Lidl', 105.90, '2025-08-05', FALSE, NULL),

-- Ropa (Id 11)
(11, 'Compra en Zara', 60.00, '2025-02-15', FALSE, 19),
(11, 'Compra en Bershka', 45.50, '2025-03-22', FALSE, 20),
(11, 'Compra en Pull&Bear', 70.00, '2025-04-18', FALSE, NULL),
(11, 'Compra en H&M', 55.30, '2025-06-07', FALSE, NULL),
(11, 'Compra en Zara', 80.25, '2025-08-12', FALSE, 19),
(11, 'Compra en Lefties', 40.00, '2025-03-05', FALSE, NULL),
(11, 'Compra en Mango', 95.00, '2025-05-10', FALSE, NULL),
(11, 'Compra en Zara', 65.75, '2025-07-09', FALSE, 19),
(11, 'Compra en Bershka', 72.00, '2025-09-01', FALSE, 20),
(11, 'Compra en H&M', 50.50, '2025-09-04', FALSE, NULL),

-- Cenas (Id 12)
(12, 'Cena en El Faro', 45.00, '2025-02-20', FALSE, 23),
(12, 'Cena en Provenzal', 60.00, '2025-03-25', FALSE, 24),
(12, 'Cena en El Café de Ana', 30.50, '2025-04-11', FALSE, 25),
(12, 'Cena en El Faro', 55.75, '2025-06-05', FALSE, 23),
(12, 'Cena en Provenzal', 48.20, '2025-07-17', FALSE, 24),
(12, 'Cena en El Faro', 52.00, '2025-03-08', FALSE, 23),
(12, 'Cena en El Café de Ana', 38.50, '2025-05-19', FALSE, 25),
(12, 'Cena en Provenzal', 61.25, '2025-06-25', FALSE, 24),
(12, 'Cena en El Faro', 47.80, '2025-08-10', FALSE, 23),
(12, 'Cena en El Café de Ana', 35.90, '2025-09-03', FALSE, 25),

-- Donaciones (Id 13)
(13, 'Donacion en Save the Change', 15, '2025-05-05', FALSE, 21),
(13, 'Donación Save the Children', 25.00, '2025-01-10', TRUE, NULL),
(13, 'Donación Médicos sin Fronteras', 30.00, '2025-03-12', FALSE, NULL),
(13, 'Donación Cáritas', 40.00, '2025-05-15', FALSE, NULL),
(13, 'Donación Cruz Roja', 35.00, '2025-07-19', FALSE, NULL),
(13, 'Donación WWF', 20.00, '2025-09-01', FALSE, NULL),
(13, 'Donación Unicef', 28.00, '2025-02-14', FALSE, NULL),
(13, 'Donación Cruz Roja', 33.00, '2025-04-09', FALSE, NULL),
(13, 'Donación Cáritas', 45.00, '2025-06-22', FALSE, NULL),
(13, 'Donación Médicos sin Fronteras', 29.00, '2025-08-05', FALSE, NULL),
(13, 'Donación Save the Children', 31.00, '2025-09-04', FALSE, NULL),

-- Casa (Id 14)
(14, 'Compra lámpara Ikea', 45.00, '2025-06-10', FALSE, NULL),
(14, 'Pago fontanero', 80.00, '2025-07-08', FALSE, NULL),
(14, 'Compra utensilios hogar', 60.00, '2025-08-15', FALSE, NULL),
(14, 'Compra mesa comedor', 150.00, '2025-09-02', FALSE, NULL),
(14, 'Compra sillas salón', 120.00, '2025-09-04', FALSE, NULL),
(14, 'Compra armario Ikea', 200.00, '2025-06-25', FALSE, NULL),
(14, 'Compra cortinas', 50.00, '2025-07-20', FALSE, NULL),
(14, 'Compra decoración salón', 35.00, '2025-08-30', FALSE, NULL),
(14, 'Pago pintor', 180.00, '2025-09-03', FALSE, NULL),
(14, 'Compra sofá', 300.00, '2025-09-05', FALSE, NULL),

-- Varios (Id 15)
(15, 'Compra papelería', 15.00, '2025-05-12', FALSE, NULL),
(15, 'Compra regalo cumpleaños', 35.00, '2025-05-28', FALSE, NULL),
(15, 'Compra en bazar', 20.00, '2025-06-10', FALSE, NULL),
(15, 'Compra juego de mesa', 25.00, '2025-06-20', FALSE, NULL),
(15, 'Compra accesorios móviles', 18.00, '2025-06-30', FALSE, NULL),
(15, 'Compra lámpara escritorio', 22.00, '2025-05-05', FALSE, NULL),
(15, 'Compra paraguas', 14.00, '2025-05-15', FALSE, NULL),
(15, 'Compra bolsa deporte', 28.00, '2025-06-05', FALSE, NULL),
(15, 'Compra cargador móvil', 19.00, '2025-06-15', FALSE, NULL),
(15, 'Compra auriculares baratos', 12.00, '2025-06-25', FALSE, NULL),

-- Transporte (Id 16)
(16, 'Taxi al centro', 12.50, '2025-03-15', FALSE, NULL),
(16, 'Bus interurbano', 5.00, '2025-03-25', FALSE, NULL),
(16, 'Gasolina', 45.00, '2025-04-10', FALSE, NULL),
(16, 'Revisión coche', 80.00, '2025-04-22', FALSE, NULL),
(16, 'Taxi aeropuerto', 30.00, '2025-04-28', FALSE, NULL),
(16, 'Metro al trabajo', 7.00, '2025-02-18', FALSE, NULL),
(16, 'Autobús urbano', 4.50, '2025-03-05', FALSE, NULL),
(16, 'Gasolina', 50.00, '2025-03-30', FALSE, NULL),
(16, 'Parking centro', 10.00, '2025-04-15', FALSE, NULL),
(16, 'Taxi nocturno', 20.00, '2025-04-29', FALSE, NULL),

-- Cine (Id 17)
(17, 'Entrada cine El Faro', 8.00, '2025-02-10', FALSE, 23),
(17, 'Entrada cine El Faro', 8.00, '2025-03-05', FALSE, 23),
(17, 'Entrada cine El Faro', 9.00, '2025-03-22', FALSE, 23),
(17, 'Entrada cine El Faro', 7.50, '2025-04-12', FALSE, 23),
(17, 'Entrada cine El Faro', 9.50, '2025-04-25', FALSE, 23),
(17, 'Entrada cine El Faro', 8.20, '2025-02-18', FALSE, 23),
(17, 'Entrada cine El Faro', 7.80, '2025-03-10', FALSE, 23),
(17, 'Entrada cine El Faro', 9.10, '2025-03-28', FALSE, 23),
(17, 'Entrada cine El Faro', 8.90, '2025-04-05', FALSE, 23),
(17, 'Entrada cine El Faro', 7.60, '2025-04-20', FALSE, 23),

-- Tecnología (Id 18)
(18, 'Compra teclado', 35.00, '2025-02-14', FALSE, NULL),
(18, 'Compra auriculares', 50.00, '2025-03-09', FALSE, NULL),
(18, 'Compra monitor', 180.00, '2025-04-20', FALSE, NULL),
(18, 'Compra ratón', 25.00, '2025-05-18', FALSE, NULL),
(18, 'Suscripción software', 12.00, '2025-06-01', TRUE, NULL),
(18, 'Compra memoria USB', 15.00, '2025-07-10', FALSE, NULL),
(18, 'Compra portátil', 750.00, '2025-02-25', FALSE, NULL),
(18, 'Compra impresora', 120.00, '2025-03-18', FALSE, NULL),
(18, 'Compra disco duro', 90.00, '2025-05-02', FALSE, NULL),
(18, 'Compra webcam', 60.00, '2025-07-25', FALSE, NULL);

INSERT INTO "transaction" ("Id_Account", "Id_Spending", "TransactionCode")
VALUES
(2, 25, 'TXN-2025-09-02');

DELETE FROM "establishment" WHERE "Id" = 3;