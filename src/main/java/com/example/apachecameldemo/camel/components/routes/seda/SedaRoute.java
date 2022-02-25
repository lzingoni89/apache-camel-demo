package com.example.apachecameldemo.camel.components.routes.seda;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.camel.seda.enabled", havingValue = "true", matchIfMissing = true)
public class SedaRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:ping?period=200")
                .process(exchange -> {
                    var message = new DefaultMessage(exchange);
                    message.setBody(java.time.Instant.now());
                    exchange.setMessage(message);
                })
                .to("direct:complexProcess");

        from("direct:complexProcess")
                .log(LoggingLevel.ERROR, "${body}")
                .end();
    }

}
