package org.nishikant.myfancypdfinvoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.service.InvoiceService;
import org.nishikant.service.UserService;

/*Central point to manage application's lifecycle, managing resources
* -- NO LONGER NEEDED AFTER INTRODUCTION OF APPLICATION CONTEXT*/
public class Application {
    /*Singletons - because static means they are associated with class and
    * only one instance of invoiceSe and objectMa will be available per
    * classloader
    * and final means they can be assigned only once*/

    /*UserService being created first as InvoiceService needs it -> but what if UserService also depends on some other
    * dependency then we need to create it first and pass to UserService and then pass UserService to InvoiceService
    * making this application class to bulky and wildly
    * ---> that's where Spring comes in to replace this Application class*/
    public static final UserService userService = new UserService();
    public static final InvoiceService invoiceService = new InvoiceService(userService);
    public static final ObjectMapper objectMapper = new ObjectMapper();
}
