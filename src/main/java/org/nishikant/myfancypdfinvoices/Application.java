package org.nishikant.myfancypdfinvoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.service.InvoiceService;

/*Central point to manage application's lifecycle, managing resouces*/
public class Application {
    public static final InvoiceService invoiceService = new InvoiceService();
    public static final ObjectMapper objectMapper = new ObjectMapper();
}
