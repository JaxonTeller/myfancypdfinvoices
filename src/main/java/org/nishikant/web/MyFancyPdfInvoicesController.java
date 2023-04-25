package org.nishikant.web;

import dto.InvoiceDTO;
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

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody InvoiceDTO invoiceDTO) throws Exception {
        return this.invoiceService.generateInvoicePdf(invoiceDTO.getUserId(), invoiceDTO.getAmount());
    }
}
