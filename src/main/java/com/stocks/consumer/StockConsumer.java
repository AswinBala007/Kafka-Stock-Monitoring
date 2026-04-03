package com.stocks.consumer;

import org.apache.kafka.clients.consumer.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

public class StockConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(StockConsumer.class);

    public static void main(String[] args) {

        logger.info("🚀 Starting Stock Consumer...");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "stock-group");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("stock-prices"));

        ObjectMapper mapper = new ObjectMapper();

        try {
            while (true) {

                logger.debug("Polling Kafka for messages...");

                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {

                    try {
                        Map<String, Object> stock =
                                mapper.readValue(record.value(), Map.class);

                        String symbol = (String) stock.get("symbol");
                        double price = ((Number) stock.get("price")).doubleValue();

                        logger.info("📥 Received: {} → {}", symbol, price);

                        // 🚨 Alert logic
                        if (price > 500) {
                            logger.warn("🚨 HIGH PRICE ALERT: {} → {}", symbol, price);
                        }

                    } catch (Exception e) {
                        logger.error("❌ Error processing message: {}", record.value(), e);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("❌ Consumer crashed: {}", e.getMessage(), e);
        } finally {
            logger.info("Shutting down consumer...");
            consumer.close();
        }
    }
}