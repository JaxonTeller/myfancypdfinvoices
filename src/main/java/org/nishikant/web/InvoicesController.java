package org.nishikant.web;

import org.nishikant.dto.InvoiceDTO;
import org.nishikant.model.Invoice;
import org.nishikant.service.InvoiceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
public class InvoicesController {

    private final InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public List<Invoice> getInvoices() {
        return this.invoiceService.findAll();
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody @Valid InvoiceDTO invoiceDTO) throws Exception {
        return this.invoiceService.generateInvoicePdf(invoiceDTO.getUserId(), invoiceDTO.getAmount());
    }

    /*To carry out the validations on RequestParams or PathVariables, there are 2 things you need to do:
    * 1. You need to specify a bean - MethodValidationPostProcessor
    * 2. Put the annotation @Validated on class*/
    @PostMapping("/invoices/{userId}/{amount}")
    public Invoice createInvoiceViaRequestVariables(@PathVariable @NotBlank String userId, @PathVariable @Min(10) @Max(50) Integer amount) throws Exception {
        return this.invoiceService.generateInvoicePdf(userId, amount);
    }
}
