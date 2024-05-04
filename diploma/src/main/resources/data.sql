CREATE TABLE IF NOT EXISTS bouquets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    image VARCHAR(255)
);

INSERT INTO bouquets (id, name, amount, price, description, image) VALUES
(1, 'Букет Микс', 10, 1500.0, 'description', 'bouquet_1.jpg'),
(2, 'Букет Комплимент', 10, 1800.0, 'description', 'bouquet_2.jpg'),
(3, 'Букет Свадебный', 10, 2300.0, 'description', 'bouquet_3.jpg'),
(4, 'Букет Джентельмен', 10, 5000.0, 'description', 'bouquet_4.jpg'),
(5, 'Букет Вьюга', 10, 3100.0, 'description', 'bouquet_5.jpg'),
(6, 'Букет Джунгли', 10, 2800.0, 'description', 'bouquet_6.jpg'),
(7, 'Букет Восход', 10, 1900.0, 'description', 'bouquet_7.jpg'),
(8, 'Букет Стихия', 10, 2600.0, 'description', 'bouquet_8.jpg'),
(9, 'Букет Удача', 10, 2400.0, 'description', 'bouquet_9.jpg'),
(10, 'Неизвестный букет', 10, 10.0, 'description', 'icon.jpg')
ON CONFLICT DO NOTHING;
