package org.nishikant.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.config.ApplicationConfiguration;
import org.nishikant.model.Invoice;
import org.nishikant.service.InvoiceService;
import org.nishikant.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*Servlet - A java class which can generated dynamic content
* to serve to online user.*/
public class InvoicesServlet extends HttpServlet {

    private UserService userService;
    private InvoiceService invoiceService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        /*So that predestroy can run*/
        ctx.registerShutdownHook();

        userService = ctx.getBean(UserService.class);
        invoiceService = ctx.getBean(InvoiceService.class);
        objectMapper = ctx.getBean(ObjectMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*Can be application/json or application/xml as well*/
        if(request.getRequestURI().equalsIgnoreCase("/")){
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().print(
                    "<html>\n" +
                            "<body>\n" +
                            "<h1>Hello World</h1>\n" +
                            "<p>This is my very first, embedded Tomcat, HTML Page!</p>\n" +
                            "</body>\n" +
                            "</html>");
        }else if(request.getRequestURI().equalsIgnoreCase("/invoices")){
            List<Invoice> invoices = invoiceService.findAll();
            String invoicesJSONs = objectMapper.writeValueAsString(invoices);

            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().print(invoicesJSONs);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().equalsIgnoreCase("/invoices")) {

            String userId = request.getParameter("user_id");
            Integer amount = Integer.valueOf(request.getParameter("amount"));

            Invoice invoice = null;
            try {
                invoice = invoiceService.generateInvoicePdf(userId, amount);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoice);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
