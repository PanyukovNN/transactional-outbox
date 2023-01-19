
Данный проект демонстрирует реализацию паттерна transactional outbox с использованием Debezium

Для старта необходимо запустить `docker-compose.yml`
Затем сконфигурировать debezium, выполнив запрос из файла `debezium-config`

Посмотреть топики кафки в контейнере:
docker exec -ti transactional-outbox_kafka_1 kafka-topics --list --bootstrap-server localhost:9092