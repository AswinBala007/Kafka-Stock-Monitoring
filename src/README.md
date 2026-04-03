# 🚀 Real-Time Stock Monitoring System (Kafka + Java)

## 📌 Overview

This project implements a real-time stock monitoring system using Apache Kafka and Java. It ingests live stock data from external APIs, streams it through Kafka, and processes it using consumers to generate alerts and insights.

---

## 🧠 Architecture

The system follows an event-driven architecture using Kafka for real-time data streaming and processing.

---

## 🏗️ Tech Stack

* **Backend:** Java (Kafka Producer & Consumer)
* **Streaming Platform:** Apache Kafka
* **API:** Alpha Vantage (Real-time stock data)
* **Build Tool:** Gradle

---

## 🔄 Data Flow

1. Stock data is fetched from Alpha Vantage API
2. Java Producer publishes data to Kafka topic (`stock-prices`)
3. Kafka Broker manages and distributes messages
4. Java Consumer processes data in real-time
5. Alerts are triggered based on business logic (e.g., price thresholds)

---

## 📁 Project Structure

```id="xkq3c6"
stock-kafka-app/
│── build.gradle
│── settings.gradle
│── src/main/java/com/stocks/
│   ├── producer/StockProducer.java
│   ├── consumer/StockConsumer.java
│   └── model/Stock.java
```

---

## ⚙️ Setup Instructions

### 1️⃣ Start Kafka

```bash id="1njc5u"
#For zookeeper users Kakfa v < 3.0
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties

#For Kafka+KRaft
#Generate a Cluster UUID
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
#Format Log Directories
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
bin/kafka-server-start.sh config/server.properties

#Creating Topic
bin/kafka-topics.sh --create \
--topic stock-prices \
--bootstrap-server localhost:9092
```

---

### 2️⃣ Run Producer

```bash id="6sn1od"
./gradlew runProducer
```

---

### 3️⃣ Run Consumer

```bash id="o4c33y"
./gradlew runConsumer
```

---

## 📊 Features

* 📈 Real-time stock data ingestion
* ⚡ Kafka-based streaming pipeline
* 🚨 Alert generation for price thresholds
* 🔄 Multi-stock support
* 📡 Event-driven architecture

---

## ⚠️ API Limitations

* Alpha Vantage free tier:

    * 5 requests/minute
    * 500 requests/day

---
# Kafka-stock-monitoring
