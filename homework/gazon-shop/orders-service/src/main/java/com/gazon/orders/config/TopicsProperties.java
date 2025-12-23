package com.gazon.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.topics")
public class TopicsProperties {
    private String ordersCreated;
    private String paymentResult;

    public String getOrdersCreated() {
        return ordersCreated;
    }

    public void setOrdersCreated(String ordersCreated) {
        this.ordersCreated = ordersCreated;
    }

    public String getPaymentResult() {
        return paymentResult;
    }

    public void setPaymentResult(String paymentResult) {
        this.paymentResult = paymentResult;
    }
}
