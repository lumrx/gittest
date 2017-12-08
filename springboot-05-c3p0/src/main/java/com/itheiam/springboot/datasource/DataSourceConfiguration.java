package com.itheiam.springboot.datasource;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DataSourceConfiguration {
	
	
	@Bean("dataSource")
	@Primary
	@ConfigurationProperties(prefix="c3p0")
	public DataSource getDataSource() {
		return DataSourceBuilder.create().type(ComboPooledDataSource.class).build();
	}
	
}
