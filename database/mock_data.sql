INSERT INTO "category" ("Id_User", "Name", "Icon", "CreatedAt", "DeletedAt")
VALUES
(2, 'Supermercados', 'heavy_dollar_sign', '2025-03-01', NULL),
(2, 'Ropa', 'heavy_dollar_sign', '2025-02-01', NULL),
(2, 'Cenas', 'heavy_dollar_sign', '2025-02-01', NULL),
(2, 'Donaciones', 'heavy_dollar_sign', '2025-01-01', NULL),
(2, 'Casa', 'heavy_dollar_sign', '2025-06-01', NULL),
(2, 'Varios', 'heavy_dollar_sign', '2025-05-01', '2025-07-01'),
(2, 'Transporte', 'heavy_dollar_sign', '2025-05-01', '2025-05-01'),
(2, 'Cine', 'heavy_dollar_sign', '2025-02-01', '2025-05-01'),
(2, 'Tecnología', 'heavy_dollar_sign', '2025-02-01', '2025-08-01');


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
(3, 'Reducir gastos en supermercados', 600, '2025-01-01', NULL),
(8, 'Menos compras varias', 50, '2025-02-01', '2025-07-01'),
(4, 'Reduccion de armario', 60, '2025-02-01', NULL);

INSERT INTO "spending" ("Id_Category", "Name", "Amount", "Date", "IsPeriodic", "Id_Establishment") VALUES
-- Supermercados (Id 3)
(3, 'Compra en Mercadona', 120.50, '2025-03-10', FALSE, 7),
(3, 'Compra en Aldi', 85.30, '2025-04-12', FALSE, 2),
(3, 'Compra en Amazon Fresh', 95.00, '2025-05-08', FALSE, 3),
(3, 'Compra en Mercadona', 150.00, '2025-06-14', FALSE, 7),
(3, 'Compra en Carrefour', 110.20, '2025-07-16', FALSE, NULL),
(3, 'Compra en Lidl', 98.40, '2025-08-20', FALSE, NULL),
(3, 'Compra en Mercadona', 130.75, '2025-09-05', FALSE, 7),
(3, 'Compra en Aldi', 90.00, '2025-03-28', FALSE, 2),
(3, 'Compra en Mercadona', 125.40, '2025-04-25', FALSE, 7),
(3, 'Compra en Carrefour', 135.00, '2025-05-21', FALSE, NULL),
(3, 'Compra en Amazon Fresh', 80.60, '2025-06-30', FALSE, 3),
(3, 'Compra en Lidl', 105.90, '2025-08-05', FALSE, NULL),

-- Ropa (Id 4)
(4, 'Compra en Zara', 60.00, '2025-02-15', FALSE, 4),
(4, 'Compra en Bershka', 45.50, '2025-03-22', FALSE, 5),
(4, 'Compra en Pull&Bear', 70.00, '2025-04-18', FALSE, NULL),
(4, 'Compra en H&M', 55.30, '2025-06-07', FALSE, NULL),
(4, 'Compra en Zara', 80.25, '2025-08-12', FALSE, 4),
(4, 'Compra en Lefties', 40.00, '2025-03-05', FALSE, NULL),
(4, 'Compra en Mango', 95.00, '2025-05-10', FALSE, NULL),
(4, 'Compra en Zara', 65.75, '2025-07-09', FALSE, 4),
(4, 'Compra en Bershka', 72.00, '2025-09-01', FALSE, 5),
(4, 'Compra en H&M', 50.50, '2025-09-04', FALSE, NULL),

-- Cenas (Id 5)
(5, 'Cena en El Faro', 45.00, '2025-02-20', FALSE, 8),
(5, 'Cena en Provenzal', 60.00, '2025-03-25', FALSE, 9),
(5, 'Cena en El Café de Ana', 30.50, '2025-04-11', FALSE, 10),
(5, 'Cena en El Faro', 55.75, '2025-06-05', FALSE, 8),
(5, 'Cena en Provenzal', 48.20, '2025-07-17', FALSE, 9),
(5, 'Cena en El Faro', 52.00, '2025-03-08', FALSE, 8),
(5, 'Cena en El Café de Ana', 38.50, '2025-05-19', FALSE, 10),
(5, 'Cena en Provenzal', 61.25, '2025-06-25', FALSE, 9),
(5, 'Cena en El Faro', 47.80, '2025-08-10', FALSE, 8),
(5, 'Cena en El Café de Ana', 35.90, '2025-09-03', FALSE, 10),

-- Donaciones (Id 6)
(6, 'Donacion en Save the Change', 15, '2025-05-05', FALSE, 6),
(6, 'Donación Save the Children', 25.00, '2025-01-10', TRUE, NULL),
(6, 'Donación Médicos sin Fronteras', 30.00, '2025-03-12', FALSE, NULL),
(6, 'Donación Cáritas', 40.00, '2025-05-15', FALSE, NULL),
(6, 'Donación Cruz Roja', 35.00, '2025-07-19', FALSE, NULL),
(6, 'Donación WWF', 20.00, '2025-09-01', FALSE, NULL),
(6, 'Donación Unicef', 28.00, '2025-02-14', FALSE, NULL),
(6, 'Donación Cruz Roja', 33.00, '2025-04-09', FALSE, NULL),
(6, 'Donación Cáritas', 45.00, '2025-06-22', FALSE, NULL),
(6, 'Donación Médicos sin Fronteras', 29.00, '2025-08-05', FALSE, NULL),
(6, 'Donación Save the Children', 31.00, '2025-09-04', FALSE, NULL),

-- Casa (Id 7)
(7, 'Compra lámpara Ikea', 45.00, '2025-06-10', FALSE, NULL),
(7, 'Pago fontanero', 80.00, '2025-07-08', FALSE, NULL),
(7, 'Compra utensilios hogar', 60.00, '2025-08-15', FALSE, NULL),
(7, 'Compra mesa comedor', 150.00, '2025-09-02', FALSE, NULL),
(7, 'Compra sillas salón', 120.00, '2025-09-04', FALSE, NULL),
(7, 'Compra armario Ikea', 200.00, '2025-06-25', FALSE, NULL),
(7, 'Compra cortinas', 50.00, '2025-07-20', FALSE, NULL),
(7, 'Compra decoración salón', 35.00, '2025-08-30', FALSE, NULL),
(7, 'Pago pintor', 180.00, '2025-09-03', FALSE, NULL),
(7, 'Compra sofá', 300.00, '2025-09-05', FALSE, NULL),

-- Varios (Id 8)
(8, 'Compra papelería', 15.00, '2025-05-12', FALSE, NULL),
(8, 'Compra regalo cumpleaños', 35.00, '2025-05-28', FALSE, NULL),
(8, 'Compra en bazar', 20.00, '2025-06-10', FALSE, NULL),
(8, 'Compra juego de mesa', 25.00, '2025-06-20', FALSE, NULL),
(8, 'Compra accesorios móviles', 18.00, '2025-06-30', FALSE, NULL),
(8, 'Compra lámpara escritorio', 22.00, '2025-05-05', FALSE, NULL),
(8, 'Compra paraguas', 14.00, '2025-05-15', FALSE, NULL),
(8, 'Compra bolsa deporte', 28.00, '2025-06-05', FALSE, NULL),
(8, 'Compra cargador móvil', 19.00, '2025-06-15', FALSE, NULL),
(8, 'Compra auriculares baratos', 12.00, '2025-06-25', FALSE, NULL),

-- Transporte (Id 9)
(9, 'Taxi al centro', 12.50, '2025-03-15', FALSE, NULL),
(9, 'Bus interurbano', 5.00, '2025-03-25', FALSE, NULL),
(9, 'Gasolina', 45.00, '2025-04-10', FALSE, NULL),
(9, 'Revisión coche', 80.00, '2025-04-22', FALSE, NULL),
(9, 'Taxi aeropuerto', 30.00, '2025-04-28', FALSE, NULL),
(9, 'Metro al trabajo', 7.00, '2025-02-18', FALSE, NULL),
(9, 'Autobús urbano', 4.50, '2025-03-05', FALSE, NULL),
(9, 'Gasolina', 50.00, '2025-03-30', FALSE, NULL),
(9, 'Parking centro', 10.00, '2025-04-15', FALSE, NULL),
(9, 'Taxi nocturno', 20.00, '2025-04-29', FALSE, NULL),

-- Cine (Id 10)
(10, 'Entrada cine Bahía Sur', 8.00, '2025-02-10', FALSE, NULL),
(10, 'Entrada cine Bahía Sur', 8.00, '2025-03-05', FALSE, NULL),
(10, 'Entrada cine Bahía Sur', 9.00, '2025-03-22', FALSE, NULL),
(10, 'Entrada cine Bahía Sur', 7.50, '2025-04-12', FALSE, NULL),
(10, 'Entrada cine Bahía Sur', 9.50, '2025-04-25', FALSE, NULL),
(10, 'Entrada cine Bahía Sur', 8.20, '2025-02-18', FALSE, NULL),
(10, 'Entrada cine El Corte Inglés', 7.80, '2025-03-10', FALSE, NULL),
(10, 'Entrada cine El Corte Inglés', 9.10, '2025-03-28', FALSE, NULL),
(10, 'Entrada cine El Corte Inglés', 8.90, '2025-04-05', FALSE, NULL),
(10, 'Entrada cine El Corte Inglés', 7.60, '2025-04-20', FALSE, NULL),

-- Tecnología (Id 11)
(11, 'Compra teclado', 35.00, '2025-02-14', FALSE, NULL),
(11, 'Compra auriculares', 50.00, '2025-03-09', FALSE, NULL),
(11, 'Compra monitor', 180.00, '2025-04-20', FALSE, NULL),
(11, 'Compra ratón', 25.00, '2025-05-18', FALSE, NULL),
(11, 'Suscripción software', 12.00, '2025-06-01', TRUE, NULL),
(11, 'Compra memoria USB', 15.00, '2025-07-10', FALSE, NULL),
(11, 'Compra portátil', 750.00, '2025-02-25', FALSE, NULL),
(11, 'Compra impresora', 120.00, '2025-03-18', FALSE, NULL),
(11, 'Compra disco duro', 90.00, '2025-05-02', FALSE, NULL),
(11, 'Compra webcam', 60.00, '2025-07-25', FALSE, NULL);

INSERT INTO "transaction" ("Id_Account", "Id_Spending", "TransactionCode")
VALUES
(2, 25, 'TXN-2025-09-02');

DELETE FROM "establishment" WHERE "Id" = 3;