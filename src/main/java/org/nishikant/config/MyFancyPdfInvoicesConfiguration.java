package org.nishikant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nishikant.service.InvoiceService;
import org.nishikant.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFancyPdfInvoicesConfiguration {

    @Bean
    public UserService getUserService(){
        return new UserService();
    }

    @Bean
    public InvoiceService getInvoiceService(UserService userService){
        return new InvoiceService(userService);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
