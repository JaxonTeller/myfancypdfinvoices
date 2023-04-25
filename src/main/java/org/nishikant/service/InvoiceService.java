package org.nishikant.service;

import org.nishikant.model.Invoice;
import org.nishikant.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    private final UserService userService;
    private final String cdn;
    private final JdbcTemplate jdbcTemplate;

    public InvoiceService(UserService userService, @Value(value = "${cdn.url}") String cdn, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.cdn = cdn;
        this.jdbcTemplate=jdbcTemplate;
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

    @Transactional
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
        String generatedPdfUrl = cdn + "/images/default/sample.pdf";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into invoices (user_id, pdf_url, amount) values (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            ps.setString(2, generatedPdfUrl);
            ps.setInt(3, amount);
            return ps;
        }, keyHolder);

        String uuid = !keyHolder.getKeys().isEmpty() ? keyHolder.getKeys().values().iterator().next().toString()
                : null;

        Invoice invoice = new Invoice();
        invoice.setId(uuid);
        invoice.setPdfUrl(generatedPdfUrl);
        invoice.setAmount(amount);
        invoice.setUserId(userId);
        return invoice;
    }

    @Transactional
    public List<Invoice> findAll(){
        return jdbcTemplate.query("select id, user_id, pdf_url, amount from invoices", (resultSet, rowNum) -> {
            Invoice invoice = new Invoice();
            invoice.setId(resultSet.getObject("id").toString());
            invoice.setPdfUrl(resultSet.getString("pdf_url"));
            invoice.setUserId(resultSet.getString("user_id"));
            invoice.setAmount(resultSet.getInt("amount"));
            return invoice;
        });
    }
}
