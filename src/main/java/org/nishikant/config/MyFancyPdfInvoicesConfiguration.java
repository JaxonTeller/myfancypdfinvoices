package org.nishikant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.nishikant")
public class MyFancyPdfInvoicesConfiguration {

    /*We can't add @Component to ObjectMapper class, as it is compiled class from 3rd party*/
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
