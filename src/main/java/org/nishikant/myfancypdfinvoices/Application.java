package org.nishikant.myfancypdfinvoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.service.InvoiceService;
import org.nishikant.service.UserService;

/*Central point to manage application's lifecycle, managing resources*/
public class Application {
    /*Singletons - because static means they are associated with class and
    * only one instance of invoiceSe and objectMa will be available per
    * classloader
    * and final means they can be assigned only once*/
    public static final InvoiceService invoiceService = new InvoiceService();
    public static final ObjectMapper objectMapper = new ObjectMapper();
    public static final UserService userService = new UserService();
}
