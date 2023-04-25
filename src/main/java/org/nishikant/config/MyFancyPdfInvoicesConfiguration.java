package org.nishikant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.nishikant")
@PropertySource(value = "classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties"
        , ignoreResourceNotFound = true)
public class MyFancyPdfInvoicesConfiguration {

    /*We can't add @Component to ObjectMapper class, as it is compiled class from 3rd party*/
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
