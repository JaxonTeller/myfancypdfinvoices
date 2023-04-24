package org.nishikant.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/*Immutable Invoice*/
public final class Invoice {
    private final String id, userId, pdfUrl;
    private final Integer amount;

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

    @Override
    public String toString() {
        return "Invoice{" +
                "userId='" + userId + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", amount=" + amount +
                '}';
    }
}
