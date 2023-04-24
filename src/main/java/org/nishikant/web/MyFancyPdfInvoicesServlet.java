package org.nishikant.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.model.Invoice;
import org.nishikant.service.InvoiceService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*Servlet - A java class which can generated dynamic content
* to serve to online user.*/
public class MyFancyPdfInvoicesServlet extends HttpServlet {

    /*Dependencies for the GET and POST calls*/
    private final InvoiceService invoiceService = new InvoiceService();
    private final ObjectMapper objectMapper = new ObjectMapper();
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

            Invoice invoice = invoiceService.generateInvoicePdf(userId, amount);

            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoice);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
