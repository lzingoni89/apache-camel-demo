package com.example.apachecameldemo.camel.components.routes.kafka;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class StockKafkaRoute extends RouteBuilder {

    private final String KAFKA_ENDPOINT = "kafka:%s?brokers=192.168.1.130:29092";

    @Override
    public void configure() throws Exception {
        fromF(KAFKA_ENDPOINT, "stock-live")
                .log(LoggingLevel.ERROR, "[${header.kafka.OFFSET}] [${body}]")
                .bean(StockPriceEnricher.class, "lalala")
                .toF(KAFKA_ENDPOINT, "stock-audit");
    }

    private class StockPriceEnricher {
        public String enrichStockPrice(String stockPrice) {
            return stockPrice + "," + java.time.Instant.now();
        }
        public String lalala(String stockPrice) {
            return stockPrice + "," + 25;
        }
    }

}
