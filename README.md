# driver-onboarding



### Prerequisites

Setup Local   

1. Install Mysql instance up and running
2. Start kafka server in local, commands, Go to Kafka root folder

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

4. Add the following to the application.properties
```

Timeout for connecting to queue is 60000 ms.


Spring File Max File Size 
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

```

### Building, and Running Your App Locally

1. Clone repo.
2. Install the app with `mvn clean install`.




