package com.in28minutes.microservices.camelmicroservicea.routes.a;

import org.apache.camel.builder.RouteBuilder;

// Apache Camel and Kafka integration
//@Component
public class SimpleRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        String topicName = "topic=cisco";
        String kafkaServer = "kafka:localhost:9092";
        String zooKeeperHost = "zookeeperHost=localhost&zookeeperPort=2181";
        String serializerClass = "serializerClass=kafka.serializer.StringEncoder";

        String toKafka = new StringBuilder().append(kafkaServer).append("?").append(topicName).append("&")
                .append(zooKeeperHost).append("&").append(serializerClass).toString();

        from("file:C:\\Users\\jayapanda\\Cisco\\TestFiles\\TestFile1.txt?noop=true").split().tokenize("\n").to(toKafka);
    }
}