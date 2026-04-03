package com.stocks.model;

public class Stock {
    public String symbol;
    public double price;
    public long timestamp;

    public Stock(String symbol, double price, long timestamp) {
        this.symbol = symbol;
        this.price = price;
        this.timestamp = timestamp;
    }
}