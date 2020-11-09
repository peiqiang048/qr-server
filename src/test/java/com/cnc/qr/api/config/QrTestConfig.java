package com.cnc.qr.api.config;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.ext.mysql.MySqlConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import java.sql.SQLException;

@Configuration
public class QrTestConfig {
    @Value("${spring.datasource.type}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public TransactionAwareDataSourceProxy postgresDataSource() {
        DriverManagerDataSource datasource = new DriverManagerDataSource(url, username, password);
        //datasource.setDriverClassName(driverClassName);
        datasource.setSchema(datasource.getSchema());
        return new TransactionAwareDataSourceProxy(datasource);
    }

    @Bean
    public IDatabaseConnection mysqlDataSource() throws SQLException, DatabaseUnitException {
        DriverManagerDataSource datasource = new DriverManagerDataSource(url, username, password);
        //datasource.setDriverClassName(driverClassName);
        datasource.setSchema(datasource.getSchema());
        return new MySqlConnection(new TransactionAwareDataSourceProxy(datasource).getConnection(), "zhd_sale_demo");
    }
}
