package org.nishikant.web;

import org.nishikant.model.Invoice;
import org.nishikant.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyFancyPdfInvoicesController {

    private final InvoiceService invoiceService;

    public MyFancyPdfInvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public List<Invoice> getInvoices() {
        return this.invoiceService.findAll();
    }

    @PostMapping("/invoices/{userId}/{amount}")
    public Invoice createInvoice(@PathVariable String userId, @PathVariable Integer amount) throws Exception {
        return this.invoiceService.generateInvoicePdf(userId, amount);
    }
}
