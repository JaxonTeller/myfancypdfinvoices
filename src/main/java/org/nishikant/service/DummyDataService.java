package org.nishikant.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile(value = "dev")
public class DummyDataService {

    private final InvoiceService invoiceService;

    public DummyDataService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostConstruct
    public void init() throws Exception {
        this.invoiceService.generateInvoicePdf("userId",100);
        this.invoiceService.generateInvoicePdf("userId2",200);
    }
}
