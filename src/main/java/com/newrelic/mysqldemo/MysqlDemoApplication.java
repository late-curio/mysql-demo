package com.newrelic.mysqldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlDemoApplication {

	public static void main(String[] args) {
//		try {
//			// The newInstance() call is a work around for some
//			// broken Java implementations
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
//		} catch (Exception ex) {
//			// handle the error
//		}
		SpringApplication.run(MysqlDemoApplication.class, args);
	}

}
