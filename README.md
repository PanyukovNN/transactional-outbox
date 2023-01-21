# Transactional outbox with debezium demo

### How to start project

1. Run `gradle build` on both of subprojects
   
   Because Dockerfiles of these projects just copy .jar to containers 

2. All neccessary infrastructure starts with `docker-compose.yml`:
   - postgres
   - kafka
   - debezium
   - order-manager 
   - notification-manager (2 replicas)

3. Run curl request from file `debezium-config` to configure debezium connector
4. Send request to order-manager

--- 
### Useful commands
Show kafka topics:

`docker exec -ti transactional-outbox_kafka_1 kafka-topics --list --bootstrap-server localhost:9092`