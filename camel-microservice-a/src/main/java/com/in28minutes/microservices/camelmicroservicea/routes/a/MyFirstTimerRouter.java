package com.in28minutes.microservices.camelmicroservicea.routes.a;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

    @Override
    public void configure() throws Exception {
        /*listen to a queue - instead of queue we will use timer here
        transformation - do some transformation on the message that comes in
        save into database - instead of database we will use log here
        There are two endpoints here - queue and database
        There are a lot of methods in RouteBuilder which will help us build routes
        We will use from()
        timer and log are the two endpoints that we are using
        timer and log are keywords here
        first-timer is a noun of our timer endpoint
        Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
        Either .transform() or .bean() can be used for transforming the incoming message
        Either .process()(processor) or .bean() can be used for processing a message*/
        from("timer:first-timer") //Body of message is null here
                .log("${body}")
                .transform().constant("My Constant Message")//Simple constant message
                .log("${body}")
                //.transform().constant("Time now is" + LocalDateTime.now())//repeats the same time at the time of evaluation
                //.bean("getCurrentTimeBean")//By using a bean we are getting the changing time.
                // "getCurrentTimeBean" is the name with which the Spring context creates the bean "GetCurrentTimeBean". So, what if someone changes the name of this class?
                // We can avoid hard coding the bean name and instead autowire the bean
                //.bean(getCurrentTimeBean)
                .bean(getCurrentTimeBean, "getCurrentTime")
                .log("${body}")
                .bean(simpleLoggingProcessingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer");
    }

}
//@Component
class GetCurrentTimeBean{
    //transforming returns a String here
    public String getCurrentTime(){
        return "Time now is "+ LocalDateTime.now();
    }
}
//@Component
class SimpleLoggingProcessingComponent{
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
    //processing returns void
    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}

class SimpleLoggingProcessor implements org.apache.camel.Processor {

    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());
    }
}


