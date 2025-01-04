package ru.asocial.model;

import java.time.LocalDateTime;

public class ExecutionResult {

    private Long brokerDealId;

    private String payload;

    private String result;

    private LocalDateTime executedAt;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getBrokerDealId() {
        return brokerDealId;
    }

    public void setBrokerDealId(Long brokerDealId) {
        this.brokerDealId = brokerDealId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getExecutedAt() {
        return executedAt;
    }

    public void setExecutedAt(LocalDateTime executedAt) {
        this.executedAt = executedAt;
    }
}
