package org.nishikant.service;

import org.nishikant.model.Invoice;
import org.nishikant.model.User;
import org.nishikant.myfancypdfinvoices.Application;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvoiceService {

    //Thread-safe list
    private List<Invoice> invoices = new CopyOnWriteArrayList<>();
    public Invoice generateInvoicePdf(String userId, Integer amount) throws Exception {
        /*
        call's a 3rd party library which generates PDF from userId and amount
        and upload it to CDN and return the URL of it.
        */
        User user = Application.userService.findById(userId);
        if(user==null){
            throw new Exception("User not found");
        }
        Invoice invoice = new Invoice(user.getId(), "www.google.com/samplepdfs", amount);
        invoices.add(invoice);
        return invoice;
    }

    public List<Invoice> findAll(){
        System.out.println("invoices = " + invoices);
        return this.invoices;
    }
}
