-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.6.19-log - MySQL Community Server (GPL)
-- ОС Сервера:                   Win64
-- HeidiSQL Версия:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Дамп данных таблицы rentcar2.car: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT IGNORE INTO `car` (`id_car`, `manufacturer`, `model`, `vin`, `colour`, `issueDate`, `transmission`, `engine_capacity`, `fuelConsumpting`, `engineType`, `price`, `carStatus`) VALUES
	(1, 'Chysler', 'Stratus', 'GTHCVB458VW5483', 'RED METALLIC', '2006-01-01', 'automatic', 2400, 12, 'gasoline', 580, 1),
	(2, 'Audi', 'A4 allroad', 'GHSUV168481343EVW', 'Silver Metallic', '2013-04-21', 'automatic', 1800, 11, 'diesel', 700, 1),
	(3, 'Honda', 'Accord', 'IBE1561VEF185616', 'Black', '2014-08-15', 'automatic', 2000, 12, 'gasoline', 800, 1),
	(4, 'Mercedes', 'S500', '158GHJ484POI45687', 'Silver', '2011-01-06', 'machanic', 5000, 24, 'gasoline', 1200, 1),
	(6, 'Opel', 'Vectra B', 'UVW789987789DB9', 'Green', '2006-04-06', 'mechanic', 2200, 11, 'diesel', 400, 1);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.car_status: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `car_status` DISABLE KEYS */;
INSERT IGNORE INTO `car_status` (`id_carStatus`, `carStatus`) VALUES
	(1, 'available'),
	(2, 'unavailable'),
	(3, 'broken');
/*!40000 ALTER TABLE `car_status` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.order: ~7 rows (приблизительно)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT IGNORE INTO `order` (`id_order`, `id_car`, `id_user`, `orderDate`, `returnDate`, `status`, `coast`, `cancelMessage`) VALUES
	(22, 2, 3, '2016-02-08', '2016-02-10', 2, 1400, 'Проерка отказа заказа'),
	(23, 3, 3, '2016-02-10', '2016-02-12', 1, 1600, 'Чрезмерно частые заказы с пересечением в один день'),
	(24, 4, 3, '2016-02-14', '2016-02-16', 3, 2400, ''),
	(25, 3, 3, '2016-02-04', '2016-02-06', 4, 1600, ''),
	(26, 1, 3, '2016-02-02', '2016-02-06', 4, 600, ''),
	(27, 2, 3, '2016-02-11', '2016-02-14', 2, 2100, ''),
	(29, 2, 3, '2016-02-20', '2016-02-25', 3, 3500, 'ы.аитаыдиаы');
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.order_status: ~4 rows (приблизительно)
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT IGNORE INTO `order_status` (`id_status`, `orderStatus`) VALUES
	(1, 'canceled'),
	(2, 'confirmed'),
	(3, 'unconfirmed'),
	(4, 'finished');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.penalty: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `penalty` DISABLE KEYS */;
INSERT IGNORE INTO `penalty` (`id_penalty`, `id_user`, `id_order`, `id_car`, `sum`, `message`, `isPayed`) VALUES
	(7, 3, 26, 1, 1000, '1. Помято заднее крыло - 400\r\n2. Скол фары - 200\r\n3. Оторван молдинг - 200\r\n4. Сломано крепление крышки багажника - 200', 1),
	(8, 3, 25, 3, 1100, 'царапина на бампере + туст', 0);
/*!40000 ALTER TABLE `penalty` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.user: ~5 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id_user`, `login`, `password`, `email`, `first_name`, `last_name`, `passportNumber`, `balance`, `id_role`) VALUES
	(3, 'poi', '1uHAXIqBwq50x67epeySwQ==', 'kashenok.r@mail.ru', 'Роман', 'Кашенок', 'MP2723773', 1200, 2),
	(4, 'admin', 'ISMvKXpXpadDiUoOSoAfww==', 'kashenok.r@mail.ru', 'Роман', 'Кашенок', 'MP2723772', NULL, 1),
	(5, 'zhenya_vs', 'S57iOtGaiLB2oxx0iro40g==', 'jenyaelman@gmail.com', 'Eugeniya', 'Elman', 'MP8888888', 6200, 3),
	(6, 'zhw', 'ICy5YqxZB1uWSwcVLSNLcA==', 'jane_elman@mail.ru', 'Zhenya', 'Vislous', 'MP9999999', 8400, 2),
	(7, 'roma', 'LpLRiNvb2I58FYMfSr/h/A==', 'roma@tut.by', 'Роман', 'Кашенок', 'MPO723773', NULL, 2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Дамп данных таблицы rentcar2.user_role: ~3 rows (приблизительно)
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT IGNORE INTO `user_role` (`id_role`, `role`) VALUES
	(1, 'admin'),
	(2, 'user'),
	(3, 'disabled');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
