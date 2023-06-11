# driver-onboarding



### Prerequisites

Setup Local   

1. Install Java 11 and Maven
2. Install Mysql instance up and running
3. Install Kafka and Start kafka server by running below commands in Kafka root folder

    1. Start zookeeper - 2181 Port
       a. bin/zookeeper-server-start.sh config/zookeeper.properties
    2. Start Kafka
       a. bin/kafka-server-start.sh config/server.properties
    3. Create Topic
       a. bin/kafka-topics.sh --create --topic onboard-driver --bootstrap-server localhost:9092
    4. Write Topic
       a. bin/kafka-console-producer.sh --topic onboard-driver --bootstrap-server localhost:9092
    5. List Topics
       bin/kafka-topics.sh --list --bootstrap-server localhost:9092

### Configurations
### DB Configurations 
The db configurations to be put in the following modules
    1. Auth Service
    2. Register Service
    3. Onboard Service

```
MYSQL_DB_URL=jdbc:mysql://localhost:3306/onboarding
MYSQL_DB_USER_NAME=demo
MYSQL_DB_PASSWORD=demo@123
MYSQL_DB_CONNECTION_TIMEOUT=60000
MYSQL_DB_MAX_POOLSIZE=50
MYSQL_DB_IDLE_TIMEOUT=60000
```

The file  configurations to be put in validation service

```

Spring File Max File Size 
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

```

### Building, and Running Your App Locally

1. Clone repo.
2. Install the app with `mvn clean install`.
3. Run below applications assigning different port to each service 
   1. auth-service
   2. register-service
   3. onboard-service
   4. validation-service




