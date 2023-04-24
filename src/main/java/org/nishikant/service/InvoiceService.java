package org.nishikant.service;

import com.fasterxml.jackson.core.Base64Variant;
import org.nishikant.model.Invoice;
import org.nishikant.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    //Thread-safe list
    private List<Invoice> invoices = new CopyOnWriteArrayList<>();

    private final UserService userService;

    public InvoiceService(UserService userService) {
        this.userService = userService;
    }

    /*Post the construction of InvoiceService and after all it's dependencies are added perform the downloading*/
    @PostConstruct
    public void downloadPdfTemplate(){
        System.out.println("Downloading PDF template from S3");
    }

    /*Before the graceful exit of Application context/ Spring container perform this*/
    @PreDestroy
    public void deletePdfTemplate(){
        System.out.println("Deleting PDF template from cache/local");
    }

    public Invoice generateInvoicePdf(String userId, Integer amount) throws Exception {
        /*
        call's a 3rd party library which generates PDF from userId and amount
        and upload it to CDN and return the URL of it.
        */
        /*
        the dependency has been delegated to Application class invoice service class still has
        to know where it can find the dependency, to avoid this we can add constructor based injection.
        i.e, we force the rule that if invoice service creation requires user service, so whoever creates invoice service,
        needs to pass user-service as well -> i.e, they need to inject the dependency.
        */
        User user = userService.findById(userId);
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
