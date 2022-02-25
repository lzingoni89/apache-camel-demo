package com.example.apachecameldemo.camel.components.routes.wiretap;

import com.example.apachecameldemo.camel.components.routes.wiretap.dto.TransactionDto;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

@Component
public class WiretapRoute extends RouteBuilder {

    static final String SENDER = "sender";
    static final String RECEIVER = "receiver";
    static final String AUDIT_TRANSACTION_ROUTE = "direct:audit-transaction";
    static final String AUDIT = "audit-transactions";
    static final String RABBIT_URI = "rabbitmq://192.168.1.130:5672/amq.direct?queue=%s&routingKey=%s&autoDelete=false";

    @Override
    public void configure() throws Exception {
        fromF(RABBIT_URI, SENDER, SENDER)
                .unmarshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .wireTap(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .toF(RABBIT_URI, RECEIVER, RECEIVER)
                .log(LoggingLevel.ERROR, "Money Transferred: ${body}");

        from(AUDIT_TRANSACTION_ROUTE)
                .process(this::enrichTransactionDto)
                .marshal().json(JsonLibrary.Jackson, TransactionDto.class)
                .toF(RABBIT_URI, AUDIT, AUDIT);
    }

    private void enrichTransactionDto(Exchange exchange) {
        var dto = exchange.getMessage().getBody(TransactionDto.class);
        dto.setTransactionDate(java.time.Instant.now().toString());

        var message = new DefaultMessage(exchange);
        message.setBody(dto);
        exchange.setMessage(message);
    }

}
