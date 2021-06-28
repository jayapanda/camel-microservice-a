package com.in28minutes.microservices.camelmicroservicea;

import com.in28minutes.microservices.camelmicroservicea.routes.c.ActiveMqSenderRouter;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamelMicroserviceAApplication {



	public static void main(String[] args) {

		SpringApplication.run(CamelMicroserviceAApplication.class, args);

		CamelContext ctx = new DefaultCamelContext();
		ActiveMqSenderRouter activeMqSenderRouter = new ActiveMqSenderRouter();

		try {
			ctx.addRoutes(activeMqSenderRouter);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}

}
