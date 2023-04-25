package org.nishikant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public final class Invoice {
    private String id, userId, pdfUrl;
    private Integer amount;

    public Invoice(){}

    public Invoice(String userId, String pdfUrl, Integer amount) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.pdfUrl = pdfUrl;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("pdf_url")
    public String getPdfUrl() {
        return pdfUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "userId='" + userId + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", amount=" + amount +
                '}';
    }
}
