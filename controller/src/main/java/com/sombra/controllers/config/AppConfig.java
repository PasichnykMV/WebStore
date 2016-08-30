package com.sombra.controllers.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan({"com.sombra.*"})
@Import({ SecurityConfig.class })
public class AppConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://ec2-54-217-213-199.eu-west-1.compute.amazonaws.com:5432/" +
                "d4pt5bhrt8ubu6?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        driverManagerDataSource.setUsername("kphhdkyttgkbjb");
        driverManagerDataSource.setPassword("PiaSkOeA3bwloCwuk9Tau1vTXc");
//        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/sombradb");
//        driverManagerDataSource.setUsername("postgres");
//        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
    }

}
