package com.itheiam.springboot.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataSourceConfigurationTest {
	
	@Autowired
	private DataSourceConfiguration dataSource;

	@Test
	public void test() throws SQLException {
		
		DataSource dataSource2 = dataSource.getDataSource();
		System.out.println(dataSource2.getConnection());
	}

}
