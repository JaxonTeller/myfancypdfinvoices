package org.nishikant.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "org.nishikant")
@PropertySource(value = "classpath:/application.properties")
@PropertySource(value = "classpath:/application-${spring.profiles.active}.properties"
        , ignoreResourceNotFound = true)
@EnableWebMvc
public class ApplicationConfiguration {

    /*We can't add @Component to ObjectMapper class, as it is compiled class from 3rd party*/
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public MethodValidationPostProcessor getValidation(){
        return new MethodValidationPostProcessor();
    }

    @Bean
    public DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:~/myFirstH2Database;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
