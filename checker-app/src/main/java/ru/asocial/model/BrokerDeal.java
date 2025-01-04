package ru.asocial.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BrokerDeal {

    private Long id;

    private Integer statusId;

    private Integer brokerProductTypeId;

    private Integer messageId;

    private String tradeNumber;

    private LocalDate dateForm;

    private String clientCode;

    private String securityCode;

    private BigDecimal securitiesQuantity;

    private LocalDate dealDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getBrokerProductTypeId() {
        return brokerProductTypeId;
    }

    public void setBrokerProductTypeId(Integer brokerProductTypeId) {
        this.brokerProductTypeId = brokerProductTypeId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getSecuritiesQuantity() {
        return securitiesQuantity;
    }

    public void setSecuritiesQuantity(BigDecimal securitiesQuantity) {
        this.securitiesQuantity = securitiesQuantity;
    }

    public LocalDate getDateForm() {
        return dateForm;
    }

    public void setDateForm(LocalDate dateForm) {
        this.dateForm = dateForm;
    }

    public LocalDate getDealDate() {
        return dealDate;
    }

    public void setDealDate(LocalDate dealDate) {
        this.dealDate = dealDate;
    }
}
