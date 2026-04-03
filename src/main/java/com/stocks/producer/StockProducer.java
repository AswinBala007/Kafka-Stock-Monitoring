package com.stocks.producer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stocks.model.Stock;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class StockProducer {

    private static final Logger logger =
            LoggerFactory.getLogger(StockProducer.class);

    private static final String API_KEY = "CDZD6S2ZVOMQEK23";

    public static void main(String[] args) {

        logger.info("🚀 Starting Stock Producer...");

        // Kafka setup
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        // HTTP client
        HttpClient client = HttpClient.newHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // ✅ 10+ stocks
        String[] symbols = {
                "AAPL", "TSLA", "GOOG", "AMZN", "MSFT",
                "META", "NFLX", "NVDA", "INTC", "AMD"
        };

        while (true) {
            for (String symbol : symbols) {

                try {
                    logger.debug("Fetching data for {}", symbol);

                    String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE"
                            + "&symbol=" + symbol
                            + "&apikey=" + API_KEY;

                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

                    HttpResponse<String> response =
                            client.send(request, HttpResponse.BodyHandlers.ofString());

                    JsonNode json = mapper.readTree(response.body());
                    JsonNode quote = json.get("Global Quote");

                    if (quote != null && !quote.isEmpty()) {

                        double price = quote.get("05. price").asDouble();

                        Stock stock = new Stock(symbol, price, System.currentTimeMillis());
                        String message = mapper.writeValueAsString(stock);

                        producer.send(new ProducerRecord<>("stock-prices", symbol, message));

                        logger.info("📤 Sent stock: {} → {}", symbol, price);

                    } else {
                        logger.warn("⚠️ No data received for {}", symbol);
                    }

                    Thread.sleep(15000); // API rate limit

                } catch (Exception e) {
                    logger.error("❌ Error fetching data for {}: {}", symbol, e.getMessage(), e);
                }
            }
        }
    }
}