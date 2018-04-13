package com.niit.Oshopcartbackend.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.niit.Oshopcartbackend.model" })

public class HibernateConfig {
	
	@Bean(name="DataSource")
	public DataSource getH2()
	{
		System.out.println("Hibernate Initiated.....");
		DriverManagerDataSource dmds=new DriverManagerDataSource();
		dmds.setDriverClassName("org.h2.Driver");
		dmds.setUrl("jdbc:h2:~/Onlineshopcart");
		dmds.setUsername("sa");
		dmds.setPassword("");
		
		return dmds;

}
	//All the Hibernate Properties return in this
		private Properties getHibernateProperties() {
			System.out.println("hibernate Properties");
			 
			Properties pp=new Properties();
			pp.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			pp.put("hibernate.show_sql", "true");
			pp.put("hibernate.format_sql", "true");
			
			pp.put("hibernate.hbm2ddl.auto", "update");
			
			return pp;
		}
		
		
		//sessionFactory Bean will be available
		
		@Bean
		public SessionFactory getSessionFactory(DataSource dataSource) {
			System.out.println("sessionfactory initialize");
			LocalSessionFactoryBuilder builder =new LocalSessionFactoryBuilder(dataSource);
			builder.addProperties(getHibernateProperties());
			builder.scanPackages("com.niit.Oshopcartbackend.model");
			System.out.print("SessionFactory Bean Created");
			return builder.buildSessionFactory();
			
		}
		
		//Bean for Hibernate Transaction Manager
		@Bean
		public HibernateTransactionManager getTransaction(SessionFactory sessionFactory) {
			System.out.println("transactional manager intialise");
			HibernateTransactionManager htm=new HibernateTransactionManager(sessionFactory);
			return htm;
		}
}