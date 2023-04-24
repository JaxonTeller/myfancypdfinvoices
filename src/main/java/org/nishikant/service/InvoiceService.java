package org.nishikant.service;

import org.nishikant.model.Invoice;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvoiceService {

    //Thread-safe list
    private List<Invoice> invoices = new CopyOnWriteArrayList<>();
    public Invoice generateInvoicePdf(String userId, Integer amount){
        /*
        call's a 3rd party library which generates PDF from userId and amount
        and upload it to CDN and return the URL of it.
        */
        Invoice invoice = new Invoice(userId, "www.google.com/samplepdfs", amount);
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> findAll(){
        System.out.println("invoices = " + invoices);
        return this.invoices;
    }
}
